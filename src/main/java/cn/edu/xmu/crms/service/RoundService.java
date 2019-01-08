package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.RoundDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundService
 * @Description 关于round的处理
 * @Author Hongqiwu
 * @Date 2018/12/20 0:22
 **/
@RestController
@Service
public class RoundService {
    @Autowired
    RoundDao roundDao;
    @Autowired
    TeamDao teamDao;

    private Map<String, Object> getRoundInfo(Round round) {
        Map<String, Object> roundInfo = new HashMap<>(4);
        roundInfo.put("id",round.getID());
        roundInfo.put("order",round.getRoundSerial());
        roundInfo.put("calculatePreType",round.getPresentationScoreMethod());
        roundInfo.put("calculateQueType",round.getQuestionScoreMethod());
        roundInfo.put("calculateRepType",round.getReportScoreMethod());
        roundInfo.put("courseID",round.getCourse().getID());
        roundInfo.put("courseName",round.getCourse().getCourseName());
        ArrayList<Map<String,Object>> signUpNumber = new ArrayList<>();
        for(int i = 0; i < round.getSignUpNumber().size();i++) {
            Map<String,Object> map = new HashMap<>(4);
            map.put("klassID",round.getSignUpNumber().get(i).get("klassID"));
            map.put("klassGrade",round.getSignUpNumber().get(i).get("klassGrade"));
            map.put("klassSerial",round.getSignUpNumber().get(i).get("klassSerial"));
            map.put("signUpNumber",round.getSignUpNumber().get(i).get("signUpNumber"));
            signUpNumber.add(map);
        }
        roundInfo.put("signUpNumber",signUpNumber);
        return roundInfo;
    }

    private Map<String, Object> getRoundScoreInfo(RoundScore roundScore) {
        Map<String, Object> roundScoreMap = new HashMap<>(5);
        roundScoreMap.put("round",roundScore.getRound().getRoundSerial());
        roundScoreMap.put("teamID",roundScore.getTeam().getID());
        roundScoreMap.put("teamName",roundScore.getTeam().getKlassSerial()+"-"+roundScore.getTeam().getTeamSerial());
        roundScoreMap.put("preScore",roundScore.getPresentationScore());
        roundScoreMap.put("reportScore",roundScore.getReportScore());
        roundScoreMap.put("questionScore",roundScore.getQuestionScore());
        roundScoreMap.put("totalScore",roundScore.getTotalScore());
        return roundScoreMap;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/course/{courseID}/round")
    public List<Map<String, Object>> listRoundsInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
        List<Map<String, Object>> roundInfoList = new ArrayList<>();
        List<Round> rounds = roundDao.listRoundsByCourseID(courseID);
        for(int i = 0; i < rounds.size(); i++) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("id",rounds.get(i).getID());
            map.put("order",rounds.get(i).getRoundSerial());
            roundInfoList.add(map);
        }
        return roundInfoList;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/round/{roundID}")
    public Map<String, Object> getRoundInfoByRoundID(@PathVariable("roundID") BigInteger roundID) {
        Round round = roundDao.getRoundByRoundID(roundID);
        return this.getRoundInfo(round);
    }

    @PreAuthorize("hasAuthority('teacher')")
    @PutMapping("/round/{roundID}")
    public void modifyRoundInfo(@PathVariable("roundID") BigInteger roundID,
                                             @RequestBody Round round) {
        round.setID(roundID);
        roundDao.updateRuleByRound(round);
        roundDao.updateRoundSignUpNumber(round);
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/round/{roundID}/roundscore")
    public List<Map<String, Object>> listTeamRoundScoreInfoByRoundID(@PathVariable("roundID") BigInteger roundID) {
        List<Map<String, Object>> teamScoreList = new ArrayList<>();
        List<RoundScore> roundScores = roundDao.listRoundScoreByRoundID(roundID);
        for(int i = 0; i < roundScores.size(); i++) {
            teamScoreList.add(this.getRoundScoreInfo(roundScores.get(i)));
        }
        return teamScoreList;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/round/{roundID}/team/{teamID}/roundscore")
    public Map<String, Object> getRoundScoreInfoByRoundAndTeamID(@PathVariable("roundID") BigInteger roundID,
                                                                 @PathVariable("teamID") BigInteger teamID) {
        RoundScore roundScore = roundDao.getRoundScoreByRoundAndTeamID(roundID, teamID);
        return this.getRoundScoreInfo(roundScore);
    }
}
