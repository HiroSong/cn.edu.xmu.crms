package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
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
    /**
     * @author LaiShaopeng
     * @date 2018/12/24 15:07
     */
    public List<Attendance> listAttendancesByKlassSeminarID(BigInteger klass_seminarID){
        List<BigInteger> attendanceIDList=teamMapper.listAttendancesIDByKlassSeminarID(klass_seminarID);
        List<Attendance> attendances=new ArrayList<>();
        for(int i=0;i<attendanceIDList.size();i++)
        {
            Attendance attendance=teamMapper.getAttendanceByAttendanceID(attendanceIDList.get(i));
            attendances.add(attendance);
        }
        return attendances;
    }
    /**
     * @author LaiShaopeng
     * @date 2018/12/24 15:33
     */
    public String getTeamNumberByTeamID(BigInteger teamID){
        Team team=teamMapper.getTeamByTeamID(teamID);
        Klass klass=klassMapper.getKlassByKlassID(team.getKlassID());
        String teamNumber=klass.getKlassSerial().toString();
        teamNumber+='-';
        teamNumber+=team.getTeamSerial().toString();
        return teamNumber;
    }
    /**
     * @author LaiShaopeng
     * @date 2018/12/24 18:10
     */
    public Attendance createAnAttendance(BigInteger klass_seminarID,BigInteger teamID,Integer teamOrder)
    {
        Attendance attendance=new Attendance();
        attendance.setKlassSeminarID(klass_seminarID);
        attendance.setTeamID(teamID);
        attendance.setTeamOrder(teamOrder);
        return attendance;
    }
    public void deleteAttendance(BigInteger attendanceID)
    {
        teamMapper.deleteAttendance(attendanceID);
    }
}
