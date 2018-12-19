package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Round;
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
}
