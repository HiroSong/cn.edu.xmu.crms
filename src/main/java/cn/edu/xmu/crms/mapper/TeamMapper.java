package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName TeamMapper
 * @Description 有关数据库中组队信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface TeamMapper {
    /**
     * 通过学生和课程ID获取队伍ID
     *
     * @param studentID 学生ID
     * @param courseID 课程ID
     * @return BigInteger 队伍Id
     * @author Hongqiwu
     * @date 2018/11/30 18:45
     */
    BigInteger getTeamIDByStudentAndCourseID(BigInteger studentID, BigInteger courseID);
    /**
     * 通过teamID获取team对象
     *
     * @param teamID 队伍ID
     * @return Team 队伍对象
     * @author Hongqiwu
     * @date 2018/11/30 19:35
     */
    Team getTeamByTeamID(BigInteger teamID);
    /**
     * 通过courseID获取teamID列表
     *
     * @param courseID 课程ID
     * @return TeamID 队伍ID列表
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    List<BigInteger> listTeamsIDByCourseID(BigInteger courseID);
}
