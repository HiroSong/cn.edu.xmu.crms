package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.TeamValidDao;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamService
 * @Author Hongqiwu
 **/
@Service
public class TeamService {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    CourseDao courseDao;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    TeamValidDao teamValidDao;
    @Autowired
    TeamValidMapper teamValidMapper;

    public List<Map<String, Object>> listTeamsInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> teamsInfoList = new ArrayList<>();
        List<Team> teams = teamDao.listTeamsByCourseID(courseID);
        for(int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            Student teamLeader = team.getLeader();
            Map<String, Object> teamLeaderMap = new HashMap<>(3);
            teamLeaderMap.put("id",teamLeader.getID());
            teamLeaderMap.put("account",teamLeader.getUsername());
            teamLeaderMap.put("name",teamLeader.getName());
            List<Student> teamMembers = team.getMembers();
            List<Map<String, Object>> teamMembersList = new ArrayList<>();
            for(int j = 0; j < teamMembers.size(); j++) {
                Student teamMember = teamMembers.get(j);
                Map<String, Object> oneMemberMap = new HashMap<>(3);
                oneMemberMap.put("id",teamMember.getID());
                oneMemberMap.put("account",teamMember.getUsername());
                oneMemberMap.put("name",teamMember.getName());
                teamMembersList.add(oneMemberMap);
            }
            Map<String, Object> oneTeamInfo = new HashMap<>(4);
            oneTeamInfo.put("name",team.getTeamName());
            oneTeamInfo.put("valid",team.getStatus());
            oneTeamInfo.put("leader",teamLeaderMap);
            oneTeamInfo.put("members",teamMembersList);
            teamsInfoList.add(oneTeamInfo);
        }
        return teamsInfoList;
    }

    public Map<String, Object> getTeamInfoByCourseAndStudentID(BigInteger courseID, BigInteger studentID) {
        Team team = teamDao.getTeamByCourseAndStudentID(courseID, studentID);
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

    public List<Map<String, Object>> listNoTeamStudentsInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> noTeamStudentsMap = new ArrayList<>();
        List<Student> noTeamStudents = studentDao.listNoTeamStudentsByCourseID(courseID);
        for(int i = 0; i < noTeamStudents.size(); i++) {
            Student noTeamStudent = noTeamStudents.get(i);
            Map<String, Object> noTeamStudentMap = new HashMap<>(3);
            noTeamStudentMap.put("id",noTeamStudent.getID());
            noTeamStudentMap.put("account",noTeamStudent.getUsername());
            noTeamStudentMap.put("name",noTeamStudent.getName());
            noTeamStudentsMap.add(noTeamStudentMap);
        }
        return noTeamStudentsMap;
    }

    public Map<String, Object> getTeamInfoByTeamID(BigInteger teamID) {
        Team team = teamDao.getTeamByTeamID(teamID);
        Map<String, Object> teamInfo = new HashMap<>(7);
        Map<String, Object> courseInfo = new HashMap<>(2);
        courseInfo.put("id",team.getCourse().getID());
        courseInfo.put("name",team.getCourse().getCourseName());
        Map<String, Object> klassInfo = new HashMap<>(2);
        klassInfo.put("id",team.getKlass().getID());
        klassInfo.put("name",team.getKlass().getGrade().toString()+team.getKlass().getKlassSerial().toString());
        Map<String, Object> leaderInfo = new HashMap<>(2);
        leaderInfo.put("id",team.getLeader().getID());
        leaderInfo.put("account",team.getLeader().getUsername());
        leaderInfo.put("name",team.getLeader().getName());
        List<Map<String, Object>> membersInfo = new ArrayList<>();
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = team.getMembers().get(i);
            Map<String, Object> memberInfo = new HashMap<>(3);
            memberInfo.put("id",student.getID());
            memberInfo.put("account",student.getUsername());
            memberInfo.put("name",student.getName());
            membersInfo.add(memberInfo);
        }
        teamInfo.put("id",team.getID());
        teamInfo.put("name",team.getTeamName());
        teamInfo.put("course",courseInfo);
        teamInfo.put("class",klassInfo);
        teamInfo.put("leader",leaderInfo);
        teamInfo.put("members",membersInfo);
        teamInfo.put("valid",team.getStatus());
        return teamInfo;
    }

    public void deleteTeamByTeamID(BigInteger teamID) {
        teamMapper.deleteTeamByTeamID(teamID);
    }

    public void insertStudentByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        teamDao.insertStudentByTeamAndStudentID(teamID,studentID);
    }

    public void deleteStudentFromTeamByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        teamMapper.deleteStudentFromTeamByTeamAndStudentID(teamID,studentID);
        Student student=studentDao.getStudentByStudentID(studentID);
        Team team=teamDao.getTeamByTeamID(teamID);
        String text=student.getName()+"同学，你已离开"+team.getTeamName()+"小组。";
        Email email=new Email();
        email.sendSimpleMail(student.getEmail(),text);
    }

    public Map<String, Object> insertApplicationByTeamValid(TeamValidApplication teamValidApplication) {
        BigInteger teacherID = teacherMapper.getTeacherIDByCourseID(teamValidApplication.getCourseID());
        teamValidApplication.setTeacherID(teacherID);
        if(teamMapper.getApplicationIDByTeamID(teamValidApplication.getTeamID())!= null) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("id",0);
            return map;
        }
        teamMapper.insertApplicationByTeamValid(teamValidApplication);
        BigInteger teamValidApplicationID = teamMapper.getLastInsertID();
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",teamValidApplicationID);
        return map;
    }

    public void updateValidApplicationByTeamID(BigInteger teamID) {
        teamMapper.updateValidApplicationByTeamID(teamID);
    }

    public Map<String, Object> createNewTeam(Team team) {
        Course course = courseDao.getCourseByCourseID(team.getCourse().getID());
        if(course == null) {
            return null;
        }
        Student leader = studentMapper.getStudentByStudentID(team.getLeader().getID());
        Klass klass = klassMapper.getKlassByKlassID(team.getKlass().getID());
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = studentMapper.getStudentByStudentID(team.getMembers().get(i).getID());
            team.getMembers().set(i,student);
        }
        team.setCourse(course);
        team.setLeader(leader);
        team.setKlass(klass);
        Team newTeam = teamDao.insertTeam(team);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",newTeam.getID());
        return map;
    }

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
            map.put("leaderID",application.getLeader().getID());
            map.put("leaderName",application.getLeader().getName());
            map.put("reason",application.getReason());
            map.put("status",application.getStatus());
            applicationMapList.add(map);
        }
        return applicationMapList;
    }

    public void updateTeamValidRequestByID(BigInteger teamValidID, Integer status) {
        TeamValidApplication application = new TeamValidApplication();
        application.setID(teamValidID);
        application.setStatus(status);
        teamValidMapper.updateStatusByID(application);
    }
}
