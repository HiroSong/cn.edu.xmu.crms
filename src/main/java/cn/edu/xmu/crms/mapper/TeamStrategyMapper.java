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

    List<TeamStrategy> listStrategyInfoByCourseID(BigInteger courseID);

    String getOptionalCourseInfo(BigInteger courseID);

    List<CourseMemberLimitStrategy> listAndCourseMemberLimitInfo(BigInteger courseID);

    List<CourseMemberLimitStrategy> listOrCourseMemberLimitInfo(BigInteger courseID);

    List<ConflictCourseStrategy> listConflictCourse(BigInteger courseID);

    void insertTeamStrategy(TeamStrategy teamStrategy);

    void insertMemberLimit(MemberLimitStrategy memberLimitStrategy);

    void insertCourseLimit(CourseMemberLimitStrategy courseMemberLimitStrategy);

    void insertTeamOr(BigInteger id, BigInteger serial);

    void insertTeamAnd(BigInteger id, BigInteger serial);

    void insertTeamAndMember(BigInteger id);

    void insertTeamAndOr(BigInteger id);

    void insertTeamAndAnd(BigInteger id);

    void insertConflict(BigInteger id, BigInteger courseID);

    List<BigInteger> listIDFromConflict();
}
