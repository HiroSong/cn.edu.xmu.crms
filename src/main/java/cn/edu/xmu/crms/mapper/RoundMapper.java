package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.RoundScore;
import cn.edu.xmu.crms.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RoundMapper
 * @Description 有关数据库中轮次信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
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


    /**
     * 根据轮次顺序和班级ID获得每个班级报名次数限制
     *
     * @return BigInteger roundID
     * @param roundID 轮次ID
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Map<String, Object> getSignUpNumberByRoundAndKlassID(BigInteger roundID, BigInteger klassID);

    void insertRound(Round round);

    void insertKlassRound(Map<String,Object> map);

    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();

    void updateSignUpNumber(Map<String,Object> map);
}
