package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.RoundDao;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.RoundMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class RoundService {
    @Autowired
    RoundDao roundDao;
    @Autowired
    RoundMapper roundMapper;
    @Autowired
    TeamMapper teamMapper;

    public List<Map<String, Object>> listRoundsInfoByCourseID(BigInteger courseID) {
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

    public Map<String, Object> getRoundInfoByRoundID(BigInteger roundID) {
        Map<String, Object> roundInfo = new HashMap<>(4);
        Round round = roundMapper.getRoundByRoundID(roundID);
        roundInfo.put("id",round.getID());
        roundInfo.put("order",round.getRoundSerial());
        roundInfo.put("calculatePreType",round.getPresentationScoreMethod());
        roundInfo.put("calculateQueType",round.getQuestionScoreMethod());
        roundInfo.put("calculateRepType",round.getReportScoreMethod());
        return roundInfo;
    }

    public void updateCalculateRuleByRound(Round round) {
        roundMapper.updateRuleByRound(round);
    }

    public List<Map<String, Object>> listTeamRoundScoreInfoByRoundID(BigInteger roundID) {
        List<Map<String, Object>> teamScoreList = new ArrayList<>();
        List<RoundScore> roundScores = roundDao.listRoundScoreByRoundID(roundID);
        for(int i = 0; i < roundScores.size(); i++) {
            Map<String, Object> teamInfo = new HashMap<>(2);
            Map<String, Object> map = new HashMap<>(4);
            RoundScore roundScore = roundScores.get(i);
            Team team = teamMapper.getTeamByTeamID(roundScore.getTeamID());
            teamInfo.put("id",team.getID());
            teamInfo.put("name",team.getTeamName());
            map.put("team",teamInfo);
            map.put("preScore",roundScore.getPresentationScore());
            map.put("questionScore",roundScore.getQuestionScore());
            map.put("reportScore",roundScore.getReportScore());
            teamScoreList.add(map);
        }
        return teamScoreList;
    }

    public Map<String, Object> getRoundScoreInfoByRoundAndTeamID(BigInteger roundID, BigInteger teamID) {
        RoundScore roundScore = roundMapper.getRoundScoreByRoundAndTeamID(roundID, teamID);
        Map<String, Object> roundScoreMap = new HashMap<>(5);
        Map<String, Object> teamMap = new HashMap<>(2);
        Team team = teamMapper.getTeamByTeamID(roundScore.getTeamID());
        teamMap.put("id",team.getID());
        teamMap.put("name",team.getTeamName());
        Map<String, Object> roundMap = new HashMap<>(5);
        Round round = roundMapper.getRoundByRoundID(roundID);
        roundMap.put("id",round.getID());
        roundMap.put("order",round.getRoundSerial());
        roundScoreMap.put("preScore",roundScore.getPresentationScore());
        roundScoreMap.put("reportScore",roundScore.getReportScore());
        roundScoreMap.put("questionScore",roundScore.getQuestionScore());
        roundScoreMap.put("team",teamMap);
        roundScoreMap.put("round",roundMap);
        return roundScoreMap;
    }

    public void updateRoundScoreInfoByRoundScore(RoundScore roundScore) {
        roundMapper.updateRoundScoreByRoundScore(roundScore);
    }
}
