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

    public Seminar getSeminarInProcess() {
        return seminarMapper.getSeminarInProcess();
    }

    public Seminar getSeminarBySeminarID(BigInteger seminarID) {
        return seminarMapper.getSeminarBySeminarID(seminarID);
    }

    private Double getTotalScore(Map<String,Object> scoreMap, BigInteger courseID) {
        Map<String, Object> scoreWeight = courseMapper.getScoreWeightByCourseID(courseID);
        Double presentationScore = new Double(scoreMap.get("presentationScore").toString());
        Double questionScore = new Double(scoreMap.get("questionScore").toString());
        Double reportScore = new Double(scoreMap.get("reportScore").toString());
        Double presentationWeight = new Double(scoreWeight.get("presentationPercentage").toString()) / 100.0;
        Double questionWeight = new Double(scoreWeight.get("questionPercentage").toString()) / 100.0;
        Double reportWeight = new Double(scoreWeight.get("reportPercentage").toString()) / 100.0;
        Double totalScore = presentationScore * presentationWeight + questionScore * questionWeight +
                reportScore * reportWeight;
        String score = String.format("%.1f", totalScore);
        totalScore = new Double(score);
        return totalScore;
    }

    private void updateRoundScore(BigInteger seminarID, BigInteger teamID,
                                  Map<String, Object> scoreMap) {
        BigInteger roundID = roundMapper.getRoundIDBySeminarID(seminarID);
        BigInteger klassID = klassMapper.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        Round round = roundMapper.getRoundByRoundID(roundID);
        List<BigInteger> seminarsID = seminarMapper.listSeminarsIDByRoundID(roundID);
        Double presentationScore = new Double(scoreMap.get("presentationScore").toString());
        Double questionScore = new Double(scoreMap.get("questionScore").toString());
        Double reportScore = new Double(scoreMap.get("reportScore").toString());
        for(int i = 0; i < seminarsID.size(); i++) {
            BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
            Double pScore = seminarMapper.getPreScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
            Double rScore = seminarMapper.getReportScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
            Double qScore = seminarMapper.getQuestionScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
            if(pScore == null) {
                pScore = 0.0;
            }
            if(rScore == null) {
                rScore = 0.0;
            }
            if(qScore == null) {
                qScore = 0.0;
            }
            if(round.getPresentationScoreMethod() == 0) {
                if(pScore > presentationScore) {
                    presentationScore = pScore;
                }
            } else {
                if(!seminarID.equals(seminarsID.get(i))) {
                    presentationScore += pScore;
                }
            }
            if(round.getPresentationScoreMethod() == 0) {
                if(rScore > reportScore) {
                    reportScore = rScore;
                }
            } else {
                if(!seminarID.equals(seminarsID.get(i))) {
                    reportScore += rScore;
                }
            }
            if(round.getPresentationScoreMethod() == 0) {
                if(qScore > questionScore) {
                    questionScore = qScore;
                }
            } else {
                if(!seminarID.equals(seminarsID.get(i))) {
                    questionScore += qScore;
                }
            }
        }
        if(round.getPresentationScoreMethod() == 1) {
            presentationScore /= seminarsID.size();
        }
        if(round.getReportScoreMethod() == 1) {
            reportScore /= seminarsID.size();
        }
        if(round.getQuestionScoreMethod() == 1) {
            questionScore /= seminarsID.size();
        }
        RoundScore roundScore = new RoundScore();
        Team team = new Team();
        roundScore.setTeam(team);
        roundScore.getTeam().setID(teamID);
        roundScore.setRound(round);
        roundScore.setPresentationScore(presentationScore);
        roundScore.setReportScore(reportScore);
        roundScore.setQuestionScore(questionScore);
        roundScore.setTotalScore(this.getTotalScore(scoreMap,courseMapper.getCourseIDByKlassID(klassID)));
        roundMapper.updateRoundScoreByRoundScore(roundScore);
    }

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
        Team team = teamMapper.getTeamByTeamID(teamID);
        BigInteger klassID = klassMapper.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        Double preScore = seminarMapper.getPreScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
        Double reportScore = seminarMapper.getReportScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
        Double questionScore = seminarMapper.getQuestionScoreByKlassSeminarAndTeamID(klassSeminarID,teamID);
        map.put("teamID",teamID);
        map.put("teamName",team.getTeamName());
        if(preScore == null) {
            map.put("presentationScore",0);
        } else {
            map.put("presentationScore",preScore);
        }
        if(reportScore == null){
            map.put("reportScore",0);
        } else {
            map.put("reportScore",reportScore);
        }
        if(questionScore == null) {
            map.put("questionScore",0);
        } else {
            map.put("questionScore",questionScore);
        }
        map.put("totalScore",this.getTotalScore(map, courseMapper.getCourseIDByKlassID(klassID)));
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
        this.updateRoundScore(seminarID,teamID,scoreMap);
        seminarMapper.updateSeminarScoreByKlassSeminarAndTeamID(scoreMap);
        scoreMap.remove("klassSeminarID");
        scoreMap.remove("teamID");
        scoreMap.put("team",teamMap);
        return scoreMap;
    }

    //创建一个新的seminar
    public BigInteger insertSeminar(Seminar seminar) {
        BigInteger roundID;
        if(seminar.getRoundOrder() != null) {
            Round round = new Round();
            round.setCourse(seminar.getCourse());
            round.setRoundSerial(seminar.getRoundOrder());
            roundMapper.insertRound(round);
            seminar.setRound(round);
            roundID = round.getID();
            seminarMapper.insertSeminar(seminar);
            BigInteger seminarID = seminar.getID();
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
                BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
                List<BigInteger> teamsID = teamMapper.listTeamsIDByKlassID(klassID);
                Map<String,Object> map = new HashMap<>(2);
                map.put("klassSeminarID",klassSeminarID);
                map.put("teamsID",teamsID);
                seminarMapper.insertSeminarScore(map);
                map.put("roundID",roundID);
                roundMapper.insertRoundScore(map);
            }
        }
        else {
            seminarMapper.insertSeminar(seminar);
            BigInteger seminarID = seminar.getID();
            List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(seminar.getCourse().getID());
            for(BigInteger klassID : klassesID) {
                Map<String,Object> map2 = new HashMap<>(2);
                map2.put("klassID",klassID);
                map2.put("seminarID",seminarID);
                seminarMapper.insertKlassSeminar(map2);
                BigInteger klassSeminarID = seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
                List<BigInteger> teamsID = teamMapper.listTeamsIDByKlassID(klassID);
                Map<String,Object> map = new HashMap<>(2);
                map.put("klassSeminarID",klassSeminarID);
                map.put("teamsID",teamsID);
                seminarMapper.insertSeminarScore(map);
            }
        }
        return seminar.getID();
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

    public BigInteger getKlassIDByProcessSeminarID(BigInteger seminarID) {
        return seminarMapper.getKlassIDByProcessSeminarID(seminarID);
    }

    public String getReportDDLBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID) {
        return seminarMapper.getReportDDLBySeminarAndKlassID(seminarID,klassID);
    }

    public Integer getStatusBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID) {
        return seminarMapper.getStatusBySeminarAndKlassID(seminarID, klassID);
    }

    public BigInteger getKlassSeminarIDBySeminarIDAndClassID(BigInteger seminarID, BigInteger klassID) {
        return seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
    }

    public BigInteger getSeminarShareIDByMainAndSubCourseID(BigInteger mainCourseID, BigInteger subCourseID) {
        return seminarMapper.getSeminarShareIDByMainAndSubCourseID(mainCourseID,subCourseID);
    }
}
