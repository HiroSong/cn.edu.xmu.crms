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

    /**
     * @author LaiShaopeng
     * @date 2018/12/24 15:07
     */
    public List<Attendance> listAttendancesByKlassSeminarID(BigInteger klass_seminarID){
        List<Attendance> attendances=teamMapper.listAttendancesByKlassSeminarID(klass_seminarID);
        return attendances;
    }
    /**
     * @author LaiShaopeng
     * @date 2018/12/24 18:10
     */
    public Attendance createAnAttendance(BigInteger klass_seminarID,BigInteger teamID,Integer teamOrder)
    {
        Attendance attendance=new Attendance();
        Team team=getTeamByTeamID(teamID);
        attendance.setKlassSeminarID(klass_seminarID);
        attendance.setTeam(team);
        attendance.setTeamOrder(teamOrder);
        return attendance;
    }

    public Integer deleteAttendance(BigInteger attendanceID)
    {
        return teamMapper.deleteAttendance(attendanceID);
    }

    public Attendance getAttendanceByKlass_SeminarIDAndTeamID(BigInteger klass_seminarID,BigInteger teamID){
        BigInteger attendanceID=teamMapper.getAttendanceIDByKlass_SeminarIDAndTeamID(klass_seminarID,teamID);
        if(attendanceID==null) {
            return null;
        }
        return teamMapper.getAttendanceByAttendanceID(attendanceID);
    }
}
