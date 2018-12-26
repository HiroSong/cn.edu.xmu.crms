package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.service.RoundService;
import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundController
 * @Author Hongqiwu
 * @Date 2018/12/20 2:06
 **/
@RestController
public class RoundController {
    @Autowired
    RoundService roundService;
    @Autowired
    SeminarService seminarService;


    @PutMapping("/round/{roundID}")
    public void modifyCalculateRuleByRoundID(@PathVariable("roundID") BigInteger roundID,
                                             @RequestBody Round round) {
        round.setID(roundID);
        roundService.updateCalculateRuleByRound(round);
    }

    @PutMapping("/round/{roundID}/team/{teamID}/roundscore")
    public void modifyRoundScoreByRoundAndTeamID(@PathVariable("roundID") BigInteger roundID,
                                                 @PathVariable("teamID") BigInteger teamID,
                                                 @RequestBody RoundScore roundScore) {
        Round round = new Round();
        round.setID(roundID);
        roundScore.setRound(round);
        Team team = new Team();
        roundScore.setTeam(team);
        roundService.updateRoundScoreInfoByRoundScore(roundScore);
    }
}