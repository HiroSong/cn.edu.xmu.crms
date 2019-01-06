package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Klass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName KlassMapper
 * @Description 有关数据库中班级信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
public interface KlassMapper {
    /**
     * 通过studentID和courseID获取klass对象
     *
     * @param studentID 学生ID
     * @param courseID 课程ID
     * @return BigInteger 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Klass getKlassByStudentAndCourseID(BigInteger studentID, BigInteger courseID);
    /**
     * 通过klassID获取Klass对象
     *
     * @param klassID 班级ID
     * @return Klass 班级对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Klass getKlassByKlassID(BigInteger klassID);
    /**
     * 通过课程ID获取班级ID列表
     *
     * @param courseID 课程ID
     * @return List<BigInteger>班级ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listKlassIDByCourseID(BigInteger courseID);

    /**
     * 通过讨论课ID获取班级列表
     *
     * @param seminarID 讨论课ID
     * @return 班级列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<Klass> listKlassesBySeminarID(BigInteger seminarID);
    /**
     * 向klass表插入klass对象
     *
     * @param klass 课程对象
     * @return BigInteger 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertKlass(Klass klass);

    /**
     * 通过klassID删除klass表信息
     *
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteKlassByKlassID(BigInteger klassID);
    /**
     * 通过klassID删除其他有关klass的表信息
     *
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteKlassRoundByKlassID(BigInteger klassID);
    /**
     * 通过klassID删除其他有关klass的表信息
     *
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteKlassSeminarByKlassID(BigInteger klassID);
    /**
     * 通过klassID删除其他有关klass的表信息
     *
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteKlassStudentByKlassID(BigInteger klassID);


    /**
     * 通过teamID和seminarID获得klassID
     *
     * @param seminarID 讨论课ID
     * @param teamID 队伍ID
     * @return BigInteger klassID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getKlassIDBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID);

    /**
     * 建立学生与班级的关联
     *
     * @param map
     * @author SongLingbing
     * @date 2018/12/26 2:18
     */
    void insertStudentKlass(Map<String, Object> map);

    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();
    /**
     * 获取班级ID
     *
     * @return BigInteger KlassID
     * @param courseID 课程ID
     * @param studentID 学生ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getKlassIDByCourseAndStudentID(BigInteger courseID,BigInteger studentID);

    /**
     * 插入klassTeam
     *
     * @param klassID 班级ID
     * @param teamID 队伍ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void insertKlassTeam(BigInteger klassID,BigInteger teamID);

}
