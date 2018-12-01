package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */
@Mapper
@Repository
public interface CourseDao {
    /**
     * 用于通过教师教工号获取教师对象
     *
     * @param id 教工号
     * @return Course  课程对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Course selectCourseByCourseId(BigInteger id);
    /**
     * 用于通过教师教工号获取课程号
     *
     * @param id 教工号
     * @return ArrayList<>  课程号
     * @author Hongqiwu
     * @date 2018/12/1 12:40
     */
    ArrayList<BigInteger> selectCourseIdByTeacherId(BigInteger id);
    /**
     * 用于通过学生号获取课程号
     *
     * @param id 学生号
     * @return ArrayList<>  课程号
     * @author Hongqiwu
     * @date 2018/12/1 12:40
     */
    ArrayList<BigInteger> selectCourseIdByStudentId(BigInteger id);
}
