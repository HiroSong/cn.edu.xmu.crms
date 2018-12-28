package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Attendance;
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
     * @param teamID 小组ID
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

}
