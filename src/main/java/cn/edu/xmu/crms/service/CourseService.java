package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Deadline;

import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:08
 */
public interface CourseService {
    /**
     * 用于根据课程号码查找课程并返回课程实例
     *
     * @param id 课程号码
     * @return Course 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public Course getCourseByCourseID(BigInteger id);
    /**
     * 用于根据时间号码查找课程并返回时间实例
     *
     * @param id 时间号码
     * @return Deadline 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public Deadline getCourseDeadlineByCourseID(BigInteger id);
    /**
     * 用于根据教师号码号码查找课程并返回课程实例
     *
     * @param id 教师号码
     * @return Course[] 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public List<Course> listCourseByTeacherID(BigInteger id);
    /**
     * 用于根据学生号码号码查找课程并返回课程实例
     *
     * @param id 学生号码
     * @return ArrayList<> 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/12/1 13:41
     */
    public List<Course> listCourseByStudentID(BigInteger id);
}
