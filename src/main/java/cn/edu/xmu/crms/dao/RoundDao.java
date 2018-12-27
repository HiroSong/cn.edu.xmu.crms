package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.mapper.RoundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

    public Round getRoundByRoundID(BigInteger roundID) {
        return roundMapper.getRoundByRoundID(roundID);
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
        return roundMapper.getRoundScoreByRoundAndTeamID(roundID,teamID);
    }
}
