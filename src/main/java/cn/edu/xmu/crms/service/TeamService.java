package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.email.Email;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamService
 * @Author Hongqiwu
 **/
@RestController
@Service
public class TeamService {
    
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    KlassDao klassDao;
    @Autowired
    TeamValidDao teamValidDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    TeamStrategyDao teamStrategyDao;


    private Map<String, Object> getTeamInfo(Team team) {
        if(team==null){
            return null;
        }
        Map<String,Object> teamInfoMap = new HashMap<>(5);
        Map<String, Object> teamLeaderMap = new HashMap<>(3);
        Student teamLeader = team.getLeader();
        teamLeaderMap.put("id",teamLeader.getID());
        teamLeaderMap.put("account",teamLeader.getUsername());
        teamLeaderMap.put("name",teamLeader.getName());
        List<Student> teamMembers = team.getMembers();
        List<Map<String, Object>> teamMembersList = new ArrayList<>();
        for(int i = 0; i < teamMembers.size(); i++) {
            Student teamMember = teamMembers.get(i);
            if(teamMember.getID().equals(teamLeader.getID())) {
                continue;
            }
            Map<String, Object> oneMemberMap = new HashMap<>(3);
            oneMemberMap.put("id",teamMember.getID());
            oneMemberMap.put("account",teamMember.getUsername());
            oneMemberMap.put("name",teamMember.getName());
            teamMembersList.add(oneMemberMap);
        }
        teamInfoMap.put("id",team.getID());
        teamInfoMap.put("teamSerial",team.getTeamSerial());
        teamInfoMap.put("klassSerial",team.getKlassSerial());
        teamInfoMap.put("name",team.getTeamName());
        teamInfoMap.put("valid",team.getStatus());
        teamInfoMap.put("leader",teamLeaderMap);
        teamInfoMap.put("members",teamMembersList);
        return teamInfoMap;
    }

    @GetMapping("/course/{courseID}/team")
    public List<Map<String, Object>> listTeamsInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
        List<Map<String, Object>> teamsInfoList = new ArrayList<>();
        List<Team> teams = teamDao.listTeamsByCourseID(courseID);
        for(int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            Map<String, Object> map = this.getTeamInfo(team);
            teamsInfoList.add(map);
        }
        return teamsInfoList;
    }

    @GetMapping("/course/{courseID}/myTeam")//查看我的队伍信息
    public Map<String, Object> getTeamInfoByCourseAndStudentID(@PathVariable("courseID") BigInteger courseID,
                                                               HttpServletRequest request) {
        BigInteger studentID = jwtTokenUtil.getIDFromRequest(request);
        Team team = teamDao.getTeamByCourseAndStudentID(courseID, studentID);
        return this.getTeamInfo(team);
    }

    @DeleteMapping("/team/{teamID}")//组长解散小组
    public void deleteTeamByTeamID(@PathVariable("teamID") BigInteger teamID) {
        teamDao.deleteTeamByTeamID(teamID);
    }

    //组员或者组长添加新的成员
    @PutMapping("/team/{teamID}/member/new")
    public Boolean addTeamMember(@PathVariable("teamID") BigInteger teamID,
                                 @RequestBody Student student) {
        teamDao.insertStudentByTeamAndStudentID(teamID,student.getID());
        Team team = new Team();
        team.setID(teamID);
        if(teamValidDao.checkTeam(teamDao.getTeamByTeamID(teamID))) {
            team.setStatus(1);
            teamDao.updateTeamStatusByID(team);
            return true;
        }
        else {
            team.setStatus(0);
            teamDao.updateTeamStatusByID(team);
            return false;
        }
    }


    //移除成员或踢出队伍
    @PutMapping("/team/{teamID}/member/old")
    public Boolean removeTeamMember(@PathVariable("teamID") BigInteger teamID,
                                    @RequestBody Student student) {
        teamDao.deleteStudentFromTeam(teamID,student.getID());
        Team team = new Team();
        team.setID(teamID);
        String emailCount = studentDao.getEmailByStudentID(student.getID());
        if(emailCount!=null){
            String teamName=teamDao.getTeamNameByTeamID(teamID);
            String emailContent=student.getName()+"同学，您已离开"+teamName+"小组.";
            Email email=new Email();
            email.sendSimpleMail(student.getEmail(),emailContent);
        }
        if(teamValidDao.checkTeam(teamDao.getTeamByTeamID(teamID))) {
            team.setStatus(1);
            teamDao.updateTeamStatusByID(team);
            return true;
        }
        else {
            team.setStatus(0);
            teamDao.updateTeamStatusByID(team);
            return false;
        }
    }

    //组长发出有效组队申请  如果返回id=0则还有未审核的申请 需等待
    @PostMapping("/team/{teamID}/teamvalidrequest")
    public Map<String, Object> createTeamValidRequest(@PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody TeamValidApplication teamValidApplication) {
        Team team = new Team();
        teamValidApplication.setTeam(team);
        teamValidApplication.getTeam().setID(teamID);
        Map<String, Object> map = new HashMap<>(1);
        if(teamValidDao.getApplicationIDByTeamID(teamValidApplication.getTeam().getID())!= null) {
            map.put("id",0);
            return map;
        }
        BigInteger teamValidApplicationID = teamValidDao.insertApplicationByTeamValid(teamValidApplication);
        map.put("id",teamValidApplicationID);
        return map;
    }


    //教师更新队伍是否合法
    @PutMapping("/request/teamvalid/{teamValidID}")
    public void updateValidApplication(@PathVariable("teamValidID") BigInteger teamValidID,
                                       @RequestBody Map<String,Integer> statusMap) {
        Integer status = statusMap.get("status");
        teamValidDao.updateValidApplicationByID(teamValidID,status);
    }


    //创建队伍 三个不合法条件（还缺一个条件，选某门课程最少最多人数）
    @PostMapping("/course/{courseID}/team")
    public Map<String, Object> createNewTeam(@PathVariable("courseID") BigInteger courseID,
                                             @RequestBody Team team) {
        Course course = courseDao.getCourseByCourseID(courseID);
        Student leader = studentDao.getStudentByStudentID(team.getLeader().getID());
        Klass klass = klassDao.getKlassByKlassID(team.getKlass().getID());
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = studentDao.getStudentByStudentID(team.getMembers().get(i).getID());
            team.getMembers().set(i,student);
        }
        team.setCourse(course);
        team.setLeader(leader);
        team.setKlass(klass);
        team.setStatus(1);
        Team newTeam = teamDao.insertTeam(team);
        if(teamStrategyDao.listStrategyInfoByCourseID(team.getCourse().getID()).size() != 0) {
            System.out.println(0);
            if(!teamValidDao.checkTeam(team)) {
                team.setStatus(0);
                teamDao.updateTeamStatusByID(team);
            }
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",newTeam.getID());
        return map;
    }

    @GetMapping("/request/teamvalid")
    public List<Map<String,Object>> listAllTeamValidApplication() {
        List<Map<String,Object>> applicationMapList = new ArrayList<>();
        List<TeamValidApplication> applications = teamValidDao.listAllApplication();
        for(int i = 0; i < applications.size(); i++) {
            Map<String,Object> map = new HashMap<>(10);
            TeamValidApplication application = applications.get(i);
            map.put("courseID",application.getCourse().getID());
            map.put("courseName",application.getCourse().getCourseName());
            map.put("classID",application.getKlass().getID());
            map.put("classGrade",application.getKlass().getGrade());
            map.put("classSerial",application.getKlass().getKlassSerial());
            map.put("teamID",application.getTeam().getID());
            map.put("teamName",application.getTeam().getTeamName());
            map.put("reason",application.getReason());
            map.put("status",application.getStatus());
            map.put("id",application.getID());
            applicationMapList.add(map);
        }

        return applicationMapList;
    }
}
