package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.mapper.KlassMapper;
import cn.edu.xmu.crms.mapper.RoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundDao
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 0:37
 **/
@Repository
public class RoundDao {
    @Autowired
    RoundMapper roundMapper;
    @Autowired
    KlassMapper klassMapper;

    public Round getRoundByRoundID(BigInteger roundID) {
        Round round = roundMapper.getRoundByRoundID(roundID);
        List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(round.getCourse().getID());
        List<Map<String,Object>> signUpNumber = new ArrayList<>();
        round.setSignUpNumber(signUpNumber);
        for(int i = 0; i < klassesID.size(); i++) {
            round.getSignUpNumber().add(roundMapper.getSignUpNumberByRoundAndKlassID(roundID,klassesID.get(i)));
        }
        return round;
    }

    public List<Round> listRoundsByCourseID(BigInteger courseID) {
        List<Round> rounds = new ArrayList<>();
        List<BigInteger> roundsID = roundMapper.listRoundIDByCourseID(courseID);
        for(int i = 0; i < roundsID.size(); i++) {
            Round round = roundMapper.getRoundByRoundID(roundsID.get(i));
            rounds.add(round);
        }
        return rounds;
    }

    public List<RoundScore> listRoundScoreByRoundID(BigInteger roundID) {
        return roundMapper.listRoundScoresByRoundID(roundID);
    }

    public RoundScore getRoundScoreByRoundAndTeamID(BigInteger roundID, BigInteger teamID) {
        RoundScore roundScore = roundMapper.getRoundScoreByRoundAndTeamID(roundID,teamID);
        return roundMapper.getRoundScoreByRoundAndTeamID(roundID,teamID);
    }

    public void updateRuleByRound(Round round) {
        roundMapper.updateRuleByRound(round);
    }

    public void updateRoundSignUpNumber(Round round) {
        List<Map<String,Object>> signUpList = round.getSignUpNumber();
        for(int i = 0; i < signUpList.size(); i++) {
            Map<String,Object> map = signUpList.get(i);
            map.put("roundID",round.getID());
            roundMapper.updateSignUpNumber(map);
        }
    }
}
