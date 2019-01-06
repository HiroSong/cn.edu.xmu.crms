package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.ConflictCourseStrategy;
import cn.edu.xmu.crms.entity.CourseMemberLimitStrategy;
import cn.edu.xmu.crms.entity.MemberLimitStrategy;
import cn.edu.xmu.crms.entity.TeamStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName TeamStrategyMapper
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/28 10:36
 **/
@Mapper
@Component
public interface TeamStrategyMapper {
    /**
     * 获取某课程组队策略信息
     *
     * @param courseID 课程
     * @return LIst 策略列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<TeamStrategy> listStrategyInfoByCourseID(BigInteger courseID);
    /**
     * 获得策略名字
     *
     * @param courseID 课程
     * @return String 策略名字
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    String getOptionalCourseInfo(BigInteger courseID);
    /**
     * 获得选修课程策略
     *
     * @param courseID 课程
     * @return List 策略列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<CourseMemberLimitStrategy> listAndCourseMemberLimitInfo(BigInteger courseID);
    /**
     * 获得选修课程策略
     *
     * @param courseID 课程
     * @return List 策略列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<CourseMemberLimitStrategy> listOrCourseMemberLimitInfo(BigInteger courseID);
    /**
     * 获得冲突课程策略
     *
     * @param courseID 课程
     * @return List 策略列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<ConflictCourseStrategy> listConflictCourse(BigInteger courseID);
    /**
     * 插入策略
     *
     * @param teamStrategy 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamStrategy(TeamStrategy teamStrategy);
    /**
     * 插入策略
     *
     * @param memberLimitStrategy 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertMemberLimit(MemberLimitStrategy memberLimitStrategy);
    /**
     * 插入策略
     *
     * @param courseMemberLimitStrategy 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertCourseLimit(CourseMemberLimitStrategy courseMemberLimitStrategy);
    /**
     * 插入策略
     *
     * @param id 策略id
     * @param serial 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamOr(BigInteger id, BigInteger serial);
    /**
     * 插入策略
     *
     * @param id 策略id
     * @param serial 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamAnd(BigInteger id, BigInteger serial);
    /**
     * 插入策略
     *
     * @param id 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamAndMember(BigInteger id);
    /**
     * 插入策略
     *
     * @param id 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamAndOr(BigInteger id);
    /**
     * 插入策略
     *
     * @param id 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamAndAnd(BigInteger id);
    /**
     * 插入策略
     *
     * @param id 策略id
     * @param courseID 组队策略
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertConflict(BigInteger id, BigInteger courseID);
    /**
     * 获得冲突课程列表
     *
     * @return List 冲突课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listIDFromConflict();
}
