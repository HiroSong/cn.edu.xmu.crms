package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.CourseMemberLimitStrategy;
import cn.edu.xmu.crms.entity.MemberLimitStrategy;
import cn.edu.xmu.crms.entity.TeamStrategy;
import cn.edu.xmu.crms.mapper.TeamStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;


/**
 * @ClassName TeamStrategyDao
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/28 10:42
 **/
@Repository
public class TeamStrategyDao {
    @Autowired
    TeamStrategyMapper teamStrategyMapper;

    public List<TeamStrategy> listStrategyInfoByCourseID(BigInteger courseID) {
        return teamStrategyMapper.listStrategyInfoByCourseID(courseID);
    }

    public void insertStrategy(Course course) {
        BigInteger courseID = course.getID();
        TeamStrategy teamStrategy = new TeamStrategy();
        MemberLimitStrategy memberLimitStrategy = new MemberLimitStrategy();
        memberLimitStrategy.setMaxMember(course.getMaxMember());
        memberLimitStrategy.setMinMember(course.getMinMember());
        teamStrategyMapper.insertMemberLimit(memberLimitStrategy);
        BigInteger id = memberLimitStrategy.getID();
        teamStrategyMapper.insertTeamAndMember(id);
        String teamOrStrategy = "TeamOrStrategy";
        if(course.getAndOr().equals(teamOrStrategy)) {
            for(int i = 0; i < course.getCourseMemberLimitStrategies().size(); i++) {
                CourseMemberLimitStrategy c = course.getCourseMemberLimitStrategies().get(i);
                teamStrategyMapper.insertCourseLimit(c);
                BigInteger serial = c.getID();
                teamStrategyMapper.insertTeamOr(id,serial);
            }
            teamStrategyMapper.insertTeamAndOr(id);
        } else {
            for(int i = 0; i < course.getCourseMemberLimitStrategies().size(); i++) {
                CourseMemberLimitStrategy c = course.getCourseMemberLimitStrategies().get(i);
                teamStrategyMapper.insertCourseLimit(c);
                BigInteger serial = c.getID();
                teamStrategyMapper.insertTeamAnd(id, serial);
            }
            teamStrategyMapper.insertTeamAndAnd(id);
        }
        teamStrategy.setCourseID(courseID);
        teamStrategy.setStrategySerial(1);
        teamStrategy.setStrategyName("TeamAndStrategy");
        teamStrategy.setStrategyID(id);
        teamStrategyMapper.insertTeamStrategy(teamStrategy);
        teamStrategy.setStrategyName("ConflictCourseStrategy");
        List<BigInteger> allID = teamStrategyMapper.listIDFromConflict();
        BigInteger conflictID = allID.get(0);
        for(int i = 1; i < allID.size(); i++) {
            if(allID.get(i).compareTo(conflictID) > 0) {
                conflictID = allID.get(i);
            }
        }
        conflictID = conflictID.add(new BigInteger("1"));
        for(int i = 0; i < course.getConflictCourseStrategies().size(); i++) {
            teamStrategy.setStrategySerial(i+2);
            BigInteger strategyID = BigInteger.valueOf(i+1);
            teamStrategy.setStrategyID(strategyID);
            teamStrategyMapper.insertTeamStrategy(teamStrategy);
            teamStrategyMapper.insertConflict(conflictID, course.getConflictCourseStrategies().get(i).getCourseID());
            i++;
            teamStrategyMapper.insertConflict(conflictID, course.getConflictCourseStrategies().get(i).getCourseID());
            conflictID = conflictID.add(new BigInteger("1"));
        }
    }
}
