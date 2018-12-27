package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Klass;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
/**
 * @ClassName KlassMapper
 * @Description 有关数据库中班级信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface KlassMapper {
    /**
     * 通过studentID和courseID获取klassID
     *
     * @param studentID 学生ID
     * @param courseID 课程ID
     * @return BigInteger 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getKlassIDByStudentAndCourseID(BigInteger studentID, BigInteger courseID);
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
     * 通过讨论课ID获取班级ID列表
     *
     * @param seminarID 讨论课ID
     * @return List<BigInteger>班级ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listKlassIDBySeminarID(BigInteger seminarID);
    /**
     * 向klass表插入klass对象
     *
     * @param klass 课程对象
     * @return BigInteger 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger insertKlassByKlass(Klass klass);
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
     * 通过klassID删除其他有关klass的表信息
     *
     * @param klassID 班级ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteKlassInTeamByKlassID(BigInteger klassID);
    /**
     * 通过teamID获得klassID
     *
     * @param teamID 队伍ID
     * @return BigInteger klassID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getKlassIDByTeamID(BigInteger teamID);
}
