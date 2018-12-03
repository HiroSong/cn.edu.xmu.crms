package cn.edu.xmu.crms.controller;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.service.SeminarService;
import cn.edu.xmu.crms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:02
 */
@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    StudentDao studentDao;
    @Autowired
    SeminarService seminarService;
    @GetMapping("/teachers/courses/{courseID}/teams")
    public List<Map<String,Object>> getTeamList(@PathVariable("courseID")
                                                      BigInteger courseID){
        List<Map<String,Object>> teamInfoList=new ArrayList<Map<String,Object>>();
        List<Team> resultTeams=teamService.listTeamByCourseID(courseID);
        Map<BigInteger,List<Student>> resultMembers=teamService.listMemberByCourseID(courseID);
        if(null==resultTeams||null==resultMembers)
            return null;
        Map<String,Map<BigInteger,String>>map=new HashMap<>();
        for(int i=0;i<resultTeams.size();i++)
        {
            Map<String,Object> teamInfo=new HashMap<String,Object>();
            teamInfo.put("teamNumber",resultTeams.get(i).getTeamNumber());

            Map<String,Object> leaderInfo=new HashMap<String,Object>();
            BigInteger leaderID=resultTeams.get(i).getTeamLeader();
            leaderInfo.put("studentID",studentDao.selectStudentByID(leaderID).getID());
            leaderInfo.put("studentName",studentDao.selectStudentByID(leaderID).getName());
            teamInfo.put("teamLeader",leaderInfo);

            List<Map<String,Object>> memberInfoList=new ArrayList<Map<String,Object>>();
            for (int j=0;j<resultMembers.get(resultTeams.get(i).getID()).size();j++)
            {
                Map<String,Object> memberInfo=new HashMap<String,Object>();
                Student member=resultMembers.get(resultTeams.get(i).getID()).get(j);
                memberInfo.put("studentID",member.getID());
                memberInfo.put("studentName",member.getName());

                memberInfoList.add(memberInfo);
            }
            teamInfo.put("teamMember",memberInfoList);

            teamInfoList.add(teamInfo);
        }
        return teamInfoList;
    }

    @GetMapping("/teachers/seminars/{seminarID}/classes/" +
            "{classID}/teams")
    public List<Map<String, Object>> getSeminarTeam(@PathVariable("seminarID")
                                              BigInteger seminarID,
                                              @PathVariable("classID")
                                              BigInteger classID){

        List<Team> teams=teamService.listPresentationTeamBySeminarIDAndClassID(seminarID,classID);
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        for(int i=0;i<teams.size();i++)
        {
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("teamID",teams.get(i).getID());
            map.put("teamName",teams.get(i).getTeamName());
            mapList.add(map);
        }
        return mapList;
    }
    @GetMapping("/students/{studentID}/courses/{courseID}/teams")
    public Map<String, Object> getStudentTeam(@PathVariable("studentID")
                                              BigInteger studentID,
                                              @PathVariable("courseID")
                                              BigInteger courseID){

        Team resultTeam=teamService.getTeamByCourseIDAndStudentID(courseID,studentID);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("teamID",resultTeam.getID());
        map.put("teamName",resultTeam.getTeamName());
        List<Student> memberList=teamService.listMemberByTeamID(resultTeam.getID());
        Student leader=studentDao.selectStudentByID(resultTeam.getTeamLeader());

        Map<String,Object> leaderInfo=new HashMap<String,Object>();
        leaderInfo.put("studentID",leader.getID());
        leaderInfo.put("studentNumber",leader.getNumber());
        leaderInfo.put("studentName",leader.getName());
        leaderInfo.put("studentStatus",true);

        List<Map<String,Object>> memberInfoList=new ArrayList<Map<String, Object>>();
        memberInfoList.add(leaderInfo);
        for(int i=0;i<memberList.size();i++)
        {
            Map<String,Object> memberInfo=new HashMap<String,Object>();
            memberInfo.put("studentID",memberList.get(i).getID());
            memberInfo.put("studentNumber",memberList.get(i).getNumber());
            memberInfo.put("studentName",memberList.get(i).getName());
            memberInfo.put("studentStatus",false);
        }

        return map;
    }
}
