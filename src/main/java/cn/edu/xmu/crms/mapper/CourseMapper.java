package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Hongqiwu
 * @Description 有关数据库中课程信息的操作
 * @date 2018/11/30 19:45
 */
@Mapper
@Repository
public interface CourseMapper {
    /**
     * 通过studentID获取courseID列表
     *
     * @param studentID 学生ID
     * @return List<BigInteger>  课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listCourseIDByStudentID(BigInteger studentID);
    /**
     * 通过teacherID获取courseID列表
     *
     * @param teacherID 教师ID
     * @return List<BigInteger>  课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listCourseIDByTeacherID(BigInteger teacherID);
    /**
     * 通过klassID获取Klass对象
     *
     * @param courseID 课程ID
     * @return Course 课程对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Course getCourseByCourseID(BigInteger courseID);
    /**
     * 通过courseID获取子课程ID
     *
     * @param courseID 课程ID
     * @return BigInteger 子课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listSubCoursesIDByCourseID(BigInteger courseID);
    /**
     * 通过courseID获取主课程ID
     *
     * @param courseID 课程ID
     * @return BigInteger 主课程ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listMainCoursesIDByCourseID(BigInteger courseID);
    /**
     * 通过主和子课程ID获取共享ID
     *
     * @param mainCourseID 主课程ID
     * @param subCourseID 子课程ID
     * @return BigInteger 共享ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getTeamShareIDByMainAndSubCourseID(BigInteger mainCourseID, BigInteger subCourseID);
    /**
     * 通过courseID获取courseMinMember
     *
     * @param courseID 课程ID
     * @return Integer 最少人数
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Integer getCourseMinMemberByCourseID(BigInteger courseID);
    /**
     * 通过courseID获取courseMaxMember
     *
     * @param courseID 课程ID
     * @return Integer 最多人数
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Integer getCourseMaxMemberByCourseID(BigInteger courseID);
    /**
     * 向course表插入course对象
     *
     * @param course 课程对象
     * @return BigInteger 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger insertCourseByCourse(Course course);
    /**
     * 通过courseID删除course表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteConflictCourseStrategyByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInKlassByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInKlassStudentByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInRoundByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInSeminarByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInTeamByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseInTeamStrategyByCourseID(BigInteger courseID);
    /**
     * 通过courseID删除其他有关course的表信息
     *
     * @param courseID 课程ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void deleteCourseMemberLimitStrategyByCourseID(BigInteger courseID);
}

