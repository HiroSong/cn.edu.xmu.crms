//package cn.edu.xmu.crms.controller;
//
//import cn.edu.xmu.crms.entity.Round;
//import cn.edu.xmu.crms.entity.RoundScore;
//import cn.edu.xmu.crms.service.RoundService;
//import cn.edu.xmu.crms.service.SeminarService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigInteger;
//import java.util.List;
//import java.util.Map;
//
///**
// * @ClassName RoundController
// * @Author Hongqiwu
// * @Date 2018/12/20 2:06
// **/
//@RestController
//public class RoundController {
//    @Autowired
//    RoundService roundService;
//    @Autowired
//    SeminarService seminarService;
//
//    @GetMapping("/round/{roundID}/seminar")
//    public List<Map<String, Object>> listSeminarInfoByRoundID(@PathVariable("roundID")
//                                                              BigInteger roundID) {
//        return seminarService.listSeminarsInfoByRoundID(roundID);
//    }
//
//    @GetMapping("/round/{roundID}")
//    public Map<String, Object> getRoundInfoByRoundID(@PathVariable("roundID")
//                                                                      BigInteger roundID) {
//        return roundService.getRoundInfoByRoundID(roundID);
//    }
//
//    @PutMapping("/round/{roundID}")
//    public void modifyCalculateRuleByRoundID(@PathVariable("roundID") BigInteger roundID,
//                                             @RequestBody Round round) {
//        round.setID(roundID);
//        roundService.updateCalculateRuleByRound(round);
//    }
//
//    @GetMapping("/round/{roundID}/roundscore")
//    public List<Map<String, Object>> listTeamRoundScoreInfoByRoundID(@PathVariable("roundID")
//                                                             BigInteger roundID) {
//        return roundService.listTeamRoundScoreInfoByRoundID(roundID);
//    }
//
//    @GetMapping("/round/{roundID}/team/{teamID}/roundscore")
//    public Map<String, Object> getRoundScoreByRoundAndTeamID(@PathVariable("roundID") BigInteger roundID,
//                                                             @PathVariable("teamID") BigInteger teamID) {
//        return roundService.getRoundScoreInfoByRoundAndTeamID(roundID, teamID);
//    }
//
//    @PutMapping("/round/{roundID}/team/{teamID}/roundscore")
//    public void modifyRoundScoreByRoundAndTeamID(@PathVariable("roundID") BigInteger roundID,
//                                                 @PathVariable("teamID") BigInteger teamID,
//                                                 @RequestBody RoundScore roundScore) {
//        roundScore.setRoundID(roundID);
//        roundScore.setTeamID(teamID);
//        roundService.updateRoundScoreInfoByRoundScore(roundScore);
//    }
//}