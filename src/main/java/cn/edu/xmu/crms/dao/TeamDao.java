package cn.edu.xmu.crms.dao;
import cn.edu.xmu.crms.entity.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 14:45
 */
@Mapper
@Repository
public interface TeamDao {
    /**
     * 用于通过队伍id获取队伍信息
     *
     * @param teamID 队伍id
     * @return Team 队伍对象
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    Team selectTeamByTeamID(BigInteger teamID);

    /**
     * 用于通过课程id获取队伍id
     *
     * @param courseID 课程id
     * @return List 队伍id列表
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    List<BigInteger> selectTeamIDListByCourseID(BigInteger courseID);

    /**
     * 用于通过小组id获取成员id
     *
     * @param teamID 小组id
     * @return List 队伍id列表
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    List<BigInteger> selectMemberIDListByTeamID(BigInteger teamID);

    /**
     * 用于通过小组成员的id获取小组id
     *
     * @param memberID 学生id
     * @return List 队伍id列表
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    List<BigInteger> selectTeamIDListByMemberID(BigInteger memberID);

    /**
     * 用于通过组长的id获取小组id
     *
     * @param leaderID 学生id
     * @return List 队伍id列表
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    List<BigInteger> selectTeamIDListByLeaderID(BigInteger leaderID);

    /**
     * 用于通过班级的id获取小组id
     *
     * @param classID 班级id
     * @return List 队伍id列表
     * @author LaiShaopeng
     * @date 2018/12/3 15:30
     */
    List<BigInteger> selectTeamIDListByClassID(BigInteger classID);
}
