package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.entity.TeamValidApplication;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
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
        leaderInfo.put("account",team.getLeader().getAccount());
        leaderInfo.put("name",team.getLeader().getStudentName());
        List<Map<String, Object>> membersInfo = new ArrayList<>();
        for(int i = 0; i < team.getMembers().size(); i++) {
            Student student = team.getMembers().get(i);
            Map<String, Object> memberInfo = new HashMap<>(3);
            memberInfo.put("id",student.getID());
            memberInfo.put("account",student.getAccount());
            memberInfo.put("name",student.getStudentName());
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
}
