package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
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
        Team team = teamDao.getTeamByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDByTeamID(teamID);
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
        Team team = teamDao.getTeamByTeamID(teamID);
        teamMap.put("id",teamID);
        teamMap.put("name",team.getTeamName());
        BigInteger klassID = klassMapper.getKlassIDByTeamID(teamID);
        BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        BigInteger courseID = courseMapper.getCourseIDByTeamID(teamID);
        Map<String, Object> scoreWeight = courseMapper.getScoreWeightByCourseID(courseID);
        Double presentationScore ;
        Object preScore = scoreMap.get("presentationScore");
        if(preScore != null) {
            presentationScore = new Double(scoreMap.get("presentationScore").toString());
        }
        else {
            presentationScore = 0.0;
        }
        Double questionScore;
        Object quesScore = scoreMap.get("questionScore");
        if(quesScore != null) {
            questionScore = new Double(scoreMap.get("questionScore").toString());
        }
        else {
            questionScore = 0.0;
        }
        Double reportScore;
        Object repScore = scoreMap.get("reportScore");
        if(repScore != null) {
            reportScore = new Double(scoreMap.get("reportScore").toString());
        }
        else {
            reportScore = 0.0;
        }
        Double presentationWeight = new Double(scoreWeight.get("presentationPercentage").toString()) / 100.0;
        Double questionWeight = new Double(scoreWeight.get("questionPercentage").toString()) / 100.0;
        Double reportWeight = new Double(scoreWeight.get("reportPercentage").toString()) / 100.0;
        Double totalScore = presentationScore * presentationWeight + questionScore * questionWeight +
                reportScore * reportWeight;
        scoreMap.put("totalScore",totalScore.toString());
        scoreMap.put("klassSeminarID",klassSeminarID);
        scoreMap.put("teamID",teamID);
        seminarMapper.updateSeminarScoreByKlassSeminarAndTeamID(scoreMap);
        scoreMap.remove("klassSeminarID");
        scoreMap.remove("teamID");
        scoreMap.put("team",teamMap);
        return scoreMap;
    }
}
