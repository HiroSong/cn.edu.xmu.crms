package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName SeminarMapper
 * @Description 有关数据库中讨论课信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface SeminarMapper {
    /**
     * 通过klassID获取SeminarID
     *
     * @param klassID 班级ID
     * @return List<BigInteger> 讨论课ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:40
     */
    List<BigInteger> listSeminarsIDByKlassID(BigInteger klassID);
    /**
     * 通过seminarID获取Seminar对象
     *
     * @param seminarID 班级ID
     * @return Seminar 讨论课对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Seminar getSeminarBySeminarID(BigInteger seminarID);
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
    BigInteger getSeminarShareIDByMainAndSubCourseID(BigInteger mainCourseID, BigInteger subCourseID);
    /**
     * 通过roundID获取讨论课ID列表
     *
     * @param roundID 轮次ID
     * @return BigInteger 讨论课ID列表
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    List<BigInteger> listSeminarsIDByRoundID(BigInteger roundID);
}
