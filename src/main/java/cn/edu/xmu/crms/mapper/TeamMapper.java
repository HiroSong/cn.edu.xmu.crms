package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Attendance;
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
     * @param courseID  课程ID
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
    void deleteKlassTeamByTeamID(BigInteger teamID);

    /**
     * 删除队伍信息
     *
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteTeamStudentByTeamID(BigInteger teamID);

    /**
     * 删除队伍信息
     *
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteTeamApplicationByTeamID(BigInteger teamID);

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
     * @param teamID    队伍ID
     * @param studentID 学生ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteStudentFromTeamByTeamAndStudentID(BigInteger teamID, BigInteger studentID);



    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();


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
     * @param klassID   班级ID
     * @param studentID 学生ID
     * @param courseID  课程ID
     * @param teamID    队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateTeamIDBy4ID(BigInteger klassID, BigInteger studentID, BigInteger courseID, BigInteger teamID);

    /**
     * 获得参与展示小组的ID列表//废弃预警
     *
     * @param klass_seminarID 具体班级下的讨论课的ID
     * @return BigInteger 参与展示小组的ID列表
     * @author LaiShaopeng
     * @date 2018/12/24 15:22
     */
    List<BigInteger> listAttendancesIDByKlassSeminarID(BigInteger klass_seminarID);

    /**
     * 根据参与展示小组的ID获得参与展示小组的对象
     *
     * @param attendanceID 参与展示小组的ID
     * @return Attendance 参与展示小组的对象
     * @author LaiShaopeng
     * @date 2018/12/24 15:28
     */
    Attendance getAttendanceByAttendanceID(BigInteger attendanceID);

    /**
     * 获得参与展示小组的列表
     *
     * @param klass_seminarID 具体班级下的讨论课的ID
     * @return AttendanceList 参与展示小组的列表
     * @author LaiShaopeng
     * @date 2018/12/25 20:48
     */
    List<Attendance> listAttendancesByKlassSeminarID(BigInteger klass_seminarID);

    /**
     * 新建参与展示的小组
     *
     * @param attendance 要报名参与展示的小组的实例
     * @author LaiShaopeng
     * @date 2018/12/24 15:28
     */
    void insertAttendance(Attendance attendance);

    /**
     * 删除参与展示的小组
     *
     * @param attendanceID 参与展示的小组的ID
     * @author LaiShaopeng
     * @date 2018/12/24 20:07
     */
    Integer deleteAttendance(BigInteger attendanceID);

    /**
     * 通过klass_seminarID和teamID获得参与展示的ID
     *
     * @param klass_seminarID 班级下的讨论课的ID
     * @param teamID          小组ID
     * @return BigInteger 参与展示的ID
     * @author LaiShaopeng
     * @date 2018/12/18 22:31
     */
    BigInteger getAttendanceIDByKlass_SeminarIDAndTeamID(BigInteger klass_seminarID, BigInteger teamID);
    /**
     * 通过学生和班级ID获取队伍ID
     *
     * @param studentID 学生ID
     * @param klassID 班级ID
     * @return BigInteger 队伍Id
     * @author LaiShaopeng
     * @date 2018/12/27 1:59
     */
    BigInteger getTeamIDByStudentAndKlassID(BigInteger studentID, BigInteger klassID);

    void updateTeamStatusByID(Team team);

    void insertStudentToTeam(BigInteger teamID, BigInteger studentID);

}
