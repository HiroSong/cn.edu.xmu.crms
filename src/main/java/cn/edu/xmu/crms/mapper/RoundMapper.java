package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
/**
 * @ClassName RoundMapper
 * @Description 有关数据库中轮次信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface RoundMapper {
    /**
     * 通过RoundID获取Round对象
     *
     * @param roundID 班级ID
     * @return Round 轮次对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Round getRoundByRoundID(BigInteger roundID);
    /**
     * 通过课程ID获取班级ID列表
     *
     * @param courseID 课程ID
     * @return List<BigInteger>班级ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listRoundIDByCourseID(BigInteger courseID);
    /**
     * 通过round对象修改数据库中round信息
     *
     * @param round 轮次对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void updateRuleByRound(Round round);
    /**
     * 通过轮次ID获取队伍ID列表
     *
     * @param roundID 轮次ID
     * @return List<BigInteger> 队伍ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<RoundScore> listRoundScoresByRoundID(BigInteger roundID);
    /**
     * 通过roundID和teamID获取某队伍某轮次成绩
     *
     * @param roundID 轮次ID
     * @param teamID 队伍ID
     * @return RoundScore 某队伍某轮次成绩
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    RoundScore getRoundScoreByRoundAndTeamID(BigInteger roundID, BigInteger teamID);
    /**
     * 通过RoundScore对象修改数据库中round_score信息
     *
     * @param roundScore 轮次成绩对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void updateRoundScoreByRoundScore(RoundScore roundScore);
    /**
     * 根据轮次顺序和课程号获得roundID
     *
     * @return BigInteger roundID
     * @param seminar 讨论课对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getRoundIDByCourseIDAndRoundSerial(Seminar seminar);
}
