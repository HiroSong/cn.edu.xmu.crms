package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.mapper.RoundMapper;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SeminarService
 * @Author Hongqiwu
 **/
@Service
public class SeminarService {

    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    RoundMapper roundMapper;

    //未完成
    public List<Map<String, Object>> listSeminarScores(BigInteger studentID, BigInteger courseID) {
        List<Map<String, Object>> listScoresInfo = new ArrayList<>();
        return listScoresInfo;
    }

    public List<Map<String, Object>> listSeminarsInfoByRoundID(BigInteger roundID) {
        List<Map<String, Object>> seminarInfoList = new ArrayList<>();
        List<Seminar> seminars = seminarDao.listSeminarsByRoundID(roundID);
        for(int i = 0; i < seminars.size(); i++) {
            Seminar seminar = seminars.get(i);
            Map<String, Object> map = new HashMap<>(3);
            map.put("id",seminar.getID());
            map.put("topic",seminar.getSeminarName());
            map.put("order",seminar.getSeminarSerial());
            seminarInfoList.add(map);
        }
        return  seminarInfoList;
    }
    public List<Map<String,Object>> listAttendanceInfoBySeminarIDAndClassID(BigInteger seminarID,BigInteger classID){
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);

        List<Map<String,Object>> attendanceInfoList=new ArrayList<>();
        List<Attendance> attendances=teamDao.listAttendancesByKlassSeminarID(klass_seminarID);
        for(int i=0;i<attendances.size();i++){
            Attendance attendance=attendances.get(i);
            Map<String,Object> map=new HashMap<>();
            map.put("teamOrder",attendance.getTeamOrder());
            map.put("teamNumber",teamDao.getTeamNumberByTeamID(attendance.getTeamID()));
            attendanceInfoList.add(map);
        }
        return attendanceInfoList;
    }
    public Map<String,Object> createNewAttendance(BigInteger seminarID,BigInteger classID,BigInteger teamID,Integer teamOrder) {
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Attendance attendance=teamDao.createAnAttendance(klass_seminarID,teamID,teamOrder);
        teamMapper.insertAttendance(attendance);
        BigInteger attendanceID=teamMapper.getLastInsertID();
        Map<String,Object> map=new HashMap<>();
        map.put("id",attendanceID);
        map.put("teamNumber",teamDao.getTeamNumberByTeamID(teamID));
        return map;
    }

    public void deleteAttendance(BigInteger attendanceID){
        teamDao.deleteAttendance(attendanceID);
    }

    public Map<String,Object> getSeminarInProgressByCourseIDAndClassID(BigInteger courseID,BigInteger klassID)
    {
        Seminar seminarInProgress=seminarDao.getSeminarInProgressByCourseIDAndClassID(courseID,klassID);
        if(seminarInProgress!=null)
        {
            Map<String,Object> map=new HashMap<>();
            Round round=roundMapper.getRoundByRoundID(seminarInProgress.getRoundID());
            map.put("id",seminarInProgress.getID());
            map.put("轮次",round.getRoundSerial());
            map.put("主题",seminarInProgress.getSeminarName());
            map.put("课次序号",seminarInProgress.getSeminarSerial());
            map.put("要求",seminarInProgress.getIntroduction());
            return map;
        }
        return null;
    }

    public Map<String,Object> checkIfAttendanceBySeminarIDAndTeamID(BigInteger seminarID,BigInteger teamID){
    }
}
