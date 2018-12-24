package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.StudentMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class TeamDao {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentDao studentDao;

    public Team getTeamByTeamID(BigInteger teamID) {
        Team team = teamMapper.getTeamByTeamID(teamID);
        BigInteger courseID = courseMapper.getCourseIDByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDByTeamID(teamID);
        BigInteger leaderID = studentMapper.getLeaderIDByTeamID(teamID);
        Course course = courseMapper.getCourseByCourseID(courseID);
        Klass klass = klassMapper.getKlassByKlassID(klassID);
        Student leader = studentMapper.getStudentByStudentID(leaderID);
        List<Student> members = studentDao.listStudentsByCourseAndTeamID(courseID,teamID);
        team.setCourse(course);
        team.setKlass(klass);
        team.setLeader(leader);
        team.setMembers(members);
        return team;
    }

    public Team getTeamByCourseAndStudentID(BigInteger courseID, BigInteger studentID) {
        BigInteger teamID = teamMapper.getTeamIDByStudentAndCourseID(studentID, courseID);
        Team team = teamMapper.getTeamByTeamID(teamID);
        return team;
    }

    public List<Team> listTeamsByCourseID(BigInteger courseID) {
        List<Team> teams = new ArrayList<>();
        List<BigInteger> teamsID = teamMapper.listTeamsIDByCourseID(courseID);
        for(int i = 0; i < teamsID.size(); i++) {
            teams.add(teamMapper.getTeamByTeamID(teamsID.get(i)));
        }
        return teams;
    }

    public void insertStudentByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        BigInteger courseID = courseMapper.getCourseIDByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDByTeamID(teamID);
        teamMapper.insertStudentIntoTeamBy4ID(klassID,studentID,courseID,teamID);
    }
}
