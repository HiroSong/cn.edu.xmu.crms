package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentDao
 * @Author Hongqiwu
 **/

@Repository
public class SeminarDao{
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    RoundMapper roundMapper;

    public List<Course> listMainCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> mainCoursesIDList = seminarMapper.listMainCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(mainCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < mainCoursesIDList.size(); i++) {
            BigInteger mainCourseID = mainCoursesIDList.get(i);
            Course course = courseDao.getCourseByCourseID(mainCourseID);
            courses.add(course);
        }
        return courses;
    }

    public List<Course> listSubCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> subCoursesIDList = seminarMapper.listSubCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(subCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < subCoursesIDList.size(); i++) {
            BigInteger subCourseID = subCoursesIDList.get(i);
            Course course = courseDao.getCourseByCourseID(subCourseID);
            courses.add(course);
        }
        return courses;
    }

    public List<Seminar> listSeminarsByRoundID(BigInteger roundID) {
        List<Seminar> seminars = new ArrayList<>();
        List<BigInteger> seminarsID = seminarMapper.listSeminarsIDByRoundID(roundID);
        for(int i = 0; i < seminarsID.size(); i++) {
            Seminar seminar = seminarMapper.getSeminarBySeminarID(seminarsID.get(i));
            seminars.add(seminar);
        }
        return seminars;
    }

    public void deleteSeminarBySeminarID(BigInteger seminarID) {
        seminarMapper.deleteSeminarBySeminarID(seminarID);
        seminarMapper.deleteKlassSeminarBySeminarID(seminarID);
    }

    public Map<String,Object> getSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID) {
        Map<String,Object> map = new HashMap<>(5);
        Map<String,Object> teamMap = new HashMap<>(2);
        Team team = teamMapper.getTeamByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        Map<String,Object> scoreMap = seminarMapper.getTeamSeminarScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
        teamMap.put("id",teamID);
        teamMap.put("name",team.getTeamName());
        map.put("team",teamMap);
        map.put("presentationScore",scoreMap.get("presentationScore"));
        map.put("reportScore",scoreMap.get("reportScore"));
        map.put("questionScore",scoreMap.get("questionScore"));
        map.put("totalScore",scoreMap.get("totalScore"));
        return map;
    }

    public Map<String, Object> updateSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                    Map<String, Object> scoreMap) {
        Map<String,Object> teamMap = new HashMap<>(2);
        Team team = teamMapper.getTeamByTeamID(teamID);
        teamMap.put("id",teamID);
        teamMap.put("name",team.getTeamName());
        BigInteger klassID = klassMapper.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        BigInteger courseID = courseMapper.getCourseIDByKlassID(klassID);
        Double totalScore = this.getTotalScore(scoreMap, courseID);
        scoreMap.put("totalScore",totalScore);
        scoreMap.put("klassSeminarID",klassSeminarID);
        scoreMap.put("teamID",teamID);
        seminarMapper.updateSeminarScoreByKlassSeminarAndTeamID(scoreMap);
        scoreMap.remove("klassSeminarID");
        scoreMap.remove("teamID");
        scoreMap.put("team",teamMap);
        return scoreMap;
    }


    //创建一个新的seminar
    public BigInteger insertSeminar(Seminar seminar) {
        BigInteger roundID;
        if(seminar.getRound().getID() == null) {//新round
            Round round = new Round();
            round.setCourse(seminar.getCourse());
            round.setRoundSerial(seminar.getRound().getRoundSerial());
            roundMapper.insertRound(round);
            roundID = roundMapper.getLastInsertID();
        }
        else {
            roundID = seminar.getRound().getID();
        }
        seminar.getRound().setID(roundID);
        seminarMapper.insertSeminar(seminar);
        BigInteger seminarID = seminarMapper.getLastInsertID();
        List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(seminar.getCourse().getID());
        for(BigInteger klassID : klassesID) {
            Map<String,Object> map1 = new HashMap<>(3);
            map1.put("klassID",klassID);
            map1.put("roundID",roundID);
            map1.put("enrollNumber",seminar.getEnrollNumber());
            roundMapper.insertKlassRound(map1);
            Map<String,Object> map2 = new HashMap<>(2);
            map2.put("klassID",klassID);
            map2.put("seminarID",seminarID);
            seminarMapper.insertKlassSeminar(map2);
        }
        return seminarID;
    }

    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID,Integer status) {
        if(status == 1) {
            seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
        } else {
            seminarMapper.updateEndSeminarByKlassAndSeminarID(klassID,seminarID);
        }
    }


    public void updateSeminarReportDDLByKlassAndSeminarID(Map<String,Object> map) {
        seminarMapper.updateSeminarReportDDLByKlassAndSeminarID(map);
    }


    public void updateSeminarBySeminarID(Seminar seminar) {
        seminarMapper.updateSeminarBySeminarID(seminar);
    }
}
