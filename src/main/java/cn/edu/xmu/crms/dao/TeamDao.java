package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.*;
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
        Team team = teamMapper.getTeamByTeamID(teamID);
        if(team == null) {
            return null;
        }
        List<Student> members = studentMapper.listMembersByTeamID(teamID);
        if(members != null) {
            team.setMembers(members);
        }
        return team;
    }

    public Team getTeamByTeamAndCourseID(BigInteger teamID,BigInteger courseID) {
        Team team = teamMapper.getTeamByTeamID(teamID);
        if(team == null) {
            return null;
        }
        List<Student> members = studentMapper.listMembersByTeamAndCourseID(teamID,courseID);
        if(members != null) {
            team.setMembers(members);
        }
        return team;
    }

    public void deleteTeamByTeamID(BigInteger teamID) {
        teamMapper.deleteTeamByTeamID(teamID);
        teamMapper.deleteKlassTeamByTeamID(teamID);
        teamMapper.deleteTeamStudentByTeamID(teamID);
        teamMapper.deleteTeamApplicationByTeamID(teamID);
    }

    public Team getTeamByCourseAndStudentID(BigInteger courseID, BigInteger studentID) {
        BigInteger teamID = teamMapper.getTeamIDByStudentAndCourseID(studentID, courseID);
        return this.getTeamByTeamAndCourseID(teamID,courseID);
    }

    //根据courseID 返回队伍列表信息
    public List<Team> listTeamsByCourseID(BigInteger courseID) {
        Course course = courseMapper.getCourseByCourseID(courseID);
        List<Team> teams = teamMapper.listTeamsByCourseID(courseID);
        for(Team team : teams) {
            team.setMembers(studentMapper.listMembersByTeamAndCourseID(team.getID(),courseID));
        }
        return teams;
    }

    //增加学生和队伍关联
    public void insertStudentByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        teamMapper.insertStudentToTeam(teamID,studentID);
    }

    //取消学生和队伍关联
    public void deleteStudentFromTeam(BigInteger teamID, BigInteger studentID) {
        teamMapper.deleteStudentFromTeamByTeamAndStudentID(teamID,studentID);
    }


    public Team insertTeam(Team team) {
        BigInteger courseID = team.getCourse().getID();
        BigInteger leaderKlassID = klassMapper.getKlassIDByCourseAndStudentID(courseID,team.getLeader().getID());
        teamMapper.insertTeam(team);
        BigInteger teamID = team.getID();
        klassMapper.insertKlassTeam(team.getKlass().getID(),teamID);
        BigInteger leaderID = team.getLeader().getID();
        teamMapper.insertStudentToTeam(teamID,leaderID);
        for(int i = 0; i < team.getMembers().size(); i++) {
            BigInteger studentID = team.getMembers().get(i).getID();
            teamMapper.insertStudentToTeam(teamID,studentID);
        }
        return team;
    }

    /**
     * @author LaiShaopeng
     * @date 2018/12/24 15:07
     */
    public List<Attendance> listAttendancesByKlassSeminarID(BigInteger klass_seminarID){
        return teamMapper.listAttendancesByKlassSeminarID(klass_seminarID);
    }
    /**
     * @author LaiShaopeng
     * @date 2018/12/24 18:10
     */
    public Attendance createAnAttendance(BigInteger klass_seminarID,BigInteger teamID,Integer teamOrder)
    {
        Attendance attendance=new Attendance();
        Team team = teamMapper.getTeamByTeamID(teamID);
        attendance.setKlassSeminarID(klass_seminarID);
        attendance.setTeam(team);
        attendance.setTeamOrder(teamOrder);
        return attendance;
    }


    public Integer deleteAttendance(BigInteger attendanceID)
    {
        return teamMapper.deleteAttendance(attendanceID);
    }

    public Attendance getAttendanceByKlassSeminarIDAndTeamID(BigInteger klass_seminarID,BigInteger teamID){
        System.out.print(klass_seminarID);
        BigInteger attendanceID=teamMapper.getAttendanceIDByKlassSeminarIDAndTeamID(klass_seminarID,teamID);
        if(attendanceID==null) {
            return null;
        }
        return teamMapper.getAttendanceByAttendanceID(attendanceID);
    }

    public void updateTeamStatusByID(Team team) {
        teamMapper.updateTeamStatusByID(team);
    }

    public  String getTeamNameByTeamID(BigInteger teamID){
        return teamMapper.getTeamNameByTeamID(teamID);
    }
}
