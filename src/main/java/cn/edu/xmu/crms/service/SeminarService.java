package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

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
    RoundMapper roundMapper;
    @Autowired
    KlassDao klassDao;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    CourseMapper courseMapper;

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

    public Map<String, Object> insertNewSeminar(Seminar seminar) {
        BigInteger roundID = roundMapper.getRoundIDByCourseIDAndRoundSerial(seminar);
        seminar.setRoundID(roundID);
        seminarMapper.insertSeminarBySeminar(seminar);
        BigInteger seminarID = seminarMapper.getLastInsertID();
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",seminarID);
        List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(seminar.getCourseID());
        for(int i = 0; i < klassesID.size(); i++) {
            seminarMapper.insertKlassSeminarBy2ID(klassesID.get(i), seminarID);
        }
        return map;
    }

    public List<Map<String, Object>> listKlassInfoBySeminarID(BigInteger seminarID) {
        List<Klass> klasses = klassDao.listKlassBySeminarID(seminarID);
        List<Map<String, Object>> klassInfoList = new ArrayList<>();
        for(int i = 0; i < klasses.size(); i++) {
            Klass klass = klasses.get(i);
            Map<String, Object> map = new HashMap<>(3);
            map.put("id",klass.getID());
            map.put("grade",klass.getGrade());
            map.put("serial",klass.getKlassSerial());
            klassInfoList.add(map);
        }
        return klassInfoList;
    }

    public void updateSeminarInfoBySeminar(Seminar seminar) {
        seminarMapper.updateSeminarBySeminarID(seminar);
    }

    public void deleteSeminarInfoBySeminarID(BigInteger seminarID) {
        seminarDao.deleteSeminarBySeminarID(seminarID);
    }

    public Map<String, Object> getSeminarInfoBySeminarID(BigInteger seminarID) {
        Map<String, Object> map = new HashMap<>(7);
        Seminar seminar = seminarMapper.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        map.put("id",seminar.getID());
        map.put("topic",seminar.getSeminarName());
        map.put("intro",seminar.getIntroduction());
        map.put("order",seminar.getSeminarSerial());
        map.put("teamNumLimit",seminar.getMaxTeam());
        map.put("signUpStartTime",seminar.getEnrollStartTime());
        map.put("signUpEndTime",seminar.getEnrollEndTime());
        return map;
    }

    public void updateSeminarReportDDLByKlassAndSeminarID(Map<String, Object> map) {
        seminarMapper.updateSeminarReportDDLByKlassAndSeminarID(map);
    }

    public Map<String, Object> getSeminarInfoBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Map<String, Object> map = new HashMap<>(9);
        Seminar seminar = seminarMapper.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        Integer status = seminarMapper.getStatusBySeminarAndKlassID(seminarID,klassID);
        Timestamp ddl = Timestamp.valueOf(seminarMapper.getReportDDLBySeminarAndKlassID(seminarID,klassID));
        map.put("id",seminar.getID());
        map.put("topic",seminar.getSeminarName());
        map.put("intro",seminar.getIntroduction());
        map.put("order",seminar.getSeminarSerial());
        map.put("teamNumLimit",seminar.getMaxTeam());
        map.put("signUpStartTime",seminar.getEnrollStartTime());
        map.put("signUpEndTime",seminar.getEnrollEndTime());
        map.put("status",status);
        map.put("reportDDL",ddl);
        return map;
    }

    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID) {
        seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
    }

    public Map<String,Object> getSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID) {
        return seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
    }

    public Map<String, Object> updateSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                    Map<String, Object> scoreMap) {
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updatePresentationScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> preScore) {
        Double presentationScore = new Double(preScore.get("presentationScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.put("presentationScore",presentationScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updateQuestionScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> quesScore) {
        Double questionScore = new Double(quesScore.get("questionScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("questionScore",questionScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updateReportScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> repScore) {
        Double reportScore = new Double(repScore.get("reportScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("reportScore",reportScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }
}
