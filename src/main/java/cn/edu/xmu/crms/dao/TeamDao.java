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
import java.util.Map;

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
        Map<String, Object> map = teamMapper.getTeamByTeamID(teamID);
        Team team = new Team();
        team.setID(new BigInteger(map.get("id").toString()));
        team.setStatus(Integer.parseInt(map.get("status").toString()));
        team.setTeamName(map.get("teamName").toString());
        team.setTeamSerial(Integer.parseInt(map.get("teamSerial").toString()));
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
        Team team = this.getTeamByTeamID(teamID);
        return team;
    }

    public List<Team> listTeamsByCourseID(BigInteger courseID) {
        List<Team> teams = new ArrayList<>();
        List<BigInteger> teamsID = teamMapper.listTeamsIDByCourseID(courseID);
        for(int i = 0; i < teamsID.size(); i++) {
            teams.add(this.getTeamByTeamID(teamsID.get(i)));
        }
        return teams;
    }

    public void insertStudentByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        BigInteger courseID = courseMapper.getCourseIDByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDByStudentAndCourseID(studentID, courseID);
        teamMapper.updateTeamIDBy4ID(klassID,studentID,courseID,teamID);
    }

    public Team insertTeam(Team team) {
        team.setStatus(1);
        if(team.getMembers().size() + 1 > team.getCourse().getMaxMember()||
                team.getMembers().size() + 1 < team.getCourse().getMinMember()) {
            team.setStatus(0);
        }
        //判断是否有学生不同班级
        for(int i = 0; i < team.getMembers().size() - 1; i++) {
            BigInteger klassID1 = klassMapper.getKlassIDByStudentAndCourseID(team.getMembers().get(i).getID(),
                    team.getCourse().getID());
            BigInteger klassID2 = klassMapper.getKlassIDByStudentAndCourseID(team.getMembers().get(i + 1).getID(),
                    team.getCourse().getID());
            if(!klassID1.equals(klassID2)||!klassID1.equals(team.getKlass().getID())) {
                team.setStatus(0);
                break;
            }
        }
        teamMapper.insertTeam(team);
        team.setID(teamMapper.getLastInsertID());
        BigInteger courseID = team.getCourse().getID();
        BigInteger teamID = team.getID();
        for(int i = 0; i < team.getMembers().size(); i++) {
            BigInteger klassID = klassMapper.getKlassIDByStudentAndCourseID(team.getMembers().get(i).getID(),courseID);
            BigInteger studentID = team.getMembers().get(i).getID();
            teamMapper.updateTeamIDBy4ID(klassID,studentID,courseID,teamID);
        }
        return team;
    }
}
