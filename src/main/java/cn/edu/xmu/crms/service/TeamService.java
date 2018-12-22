package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
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

    public List<Map<String, Object>> listTeamsInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> teamsInfoList = new ArrayList<>();
        List<Team> teams = teamDao.listTeamsByCourseID(courseID);
        for(int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            Student teamLeader = studentDao.getStudentByStudentID(team.getLeaderID());
            Map<String, Object> teamLeaderMap = new HashMap<>(3);
            teamLeaderMap.put("id",teamLeader.getID());
            teamLeaderMap.put("account",teamLeader.getAccount());
            teamLeaderMap.put("name",teamLeader.getStudentName());
            List<Student> teamMembers = studentDao.listStudentsByCourseAndTeamID(team.getCourseID(),team.getID());
            List<Map<String, Object>> teamMembersList = new ArrayList<>();
            for(int j = 0; j < teamMembers.size(); j++) {
                Student teamMember = teamMembers.get(j);
                Map<String, Object> oneMemberMap = new HashMap<>(3);
                oneMemberMap.put("id",teamMember.getID());
                oneMemberMap.put("account",teamMember.getAccount());
                oneMemberMap.put("name",teamMember.getStudentName());
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
        Student teamLeader = studentDao.getStudentByStudentID(team.getLeaderID());
        teamLeaderMap.put("id",teamLeader.getID());
        teamLeaderMap.put("account",teamLeader.getAccount());
        teamLeaderMap.put("name",teamLeader.getStudentName());
        List<Student> teamMembers = studentDao.listStudentsByCourseAndTeamID(team.getCourseID(),team.getID());
        List<Map<String, Object>> teamMembersList = new ArrayList<>();
        for(int i = 0; i < teamMembers.size(); i++) {
            Student teamMember = teamMembers.get(i);
            Map<String, Object> oneMemberMap = new HashMap<>(3);
            oneMemberMap.put("id",teamMember.getID());
            oneMemberMap.put("account",teamMember.getAccount());
            oneMemberMap.put("name",teamMember.getStudentName());
            teamMembersList.add(oneMemberMap);
        }
        teamInfoMap.put("id",team.getID());
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
            noTeamStudentMap.put("account",noTeamStudent.getAccount());
            noTeamStudentMap.put("name",noTeamStudent.getStudentName());
            noTeamStudentsMap.add(noTeamStudentMap);
        }
        return noTeamStudentsMap;
    }
}
