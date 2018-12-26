package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.entity.TeamValidApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
    /**
     * 删除队伍信息
     *
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteTeamByTeamID(BigInteger teamID);
    /**
     * 删除小组成员
     *
     * @param teamID 队伍ID
     * @param studentID 学生ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteStudentFromTeamByTeamAndStudentID(BigInteger teamID, BigInteger studentID);
    /**
     * 队伍添加新成员
     *
     * @param klassID 队伍ID
     * @param studentID 队伍ID
     * @param courseID 队伍ID
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertStudentIntoTeamBy4ID(BigInteger klassID,BigInteger studentID, BigInteger courseID, BigInteger teamID);
    /**
     * 申请额外添加组员
     *
     * @param teamValidApplication 额外组队申请
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertApplicationByTeamValid(TeamValidApplication teamValidApplication);
    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();
    /**
     * 教师同意队伍合法申请
     *
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateValidApplicationByTeamID(BigInteger teamID);
    /**
     * 获得申请ID
     *
     * @param teamID 队伍ID
     * @return BigInteger 申请ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getApplicationIDByTeamID(BigInteger teamID);
    /**
     * 插入新队伍信息
     *
     * @param team 队伍对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertTeam(Team team);
    /**
     * 更新学生组队情况
     *
     * @param klassID 班级ID
     * @param studentID 学生ID
     * @param courseID 课程ID
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateTeamIDBy4ID(BigInteger klassID,BigInteger studentID,BigInteger courseID,BigInteger teamID);


    List<Team> listTeamsByCourseID(BigInteger courseID);
}
