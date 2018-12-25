package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @ClassName TeacherMapper
 * @Author Hongqiwu
 * @Description 有关数据库中教师信息的操作
 * @Date 2018/12/18 17:37
 **/
@Mapper
@Component
public interface TeacherMapper {
    /**
     * 用于通过教师id获取教师信息
     * @param teacherID 教师id号
     * @return Teacher 教师对象
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    Teacher getTeacherByTeacherID(BigInteger teacherID);
    /**
     * 用于通过课程ID获取教师ID
     * @param courseID 教师id号
     * @return BigInteger teacherID
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    BigInteger getTeacherIDByCourseID(BigInteger courseID);

    /**
     * 创建教师用户
     *
     * @param user 用户
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    Integer insertTeacher(User user);

    /**
     * 获取所有教师
     *
     * @return 所有教师对象
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<Teacher> listAllTeachers();

    /**
     * 修改教师信息
     * @param teacher 教师对象
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    Integer updateTeacherInfoByTeacher(Teacher teacher);

    /**
     * 用于重置教师密码为123456
     *
     * @param  teacherID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer resetTeacherPasswordByTeacherID(BigInteger teacherID);

    /**
     * 用于删除单个教师
     *
     * @param  teacherID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer deleteTeacherByTeacherID(BigInteger teacherID);

    /**
     * 用于激活某个教师账号
     *
     * @param  teacher 教师账号
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer updateTeacherActiveByTeacher(Teacher teacher);

    /**
     *  通过教工号获得教师对象
     *
     * @param username 教工号
     * @return Teacher teacher
     * @author SongLingbing
     * @date 2018/12/23 21:53
     */
    Teacher getTeacherByTeacherAccount(String username);

    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();

    /**
     * 获取所有邮箱号
     *
     * @return 所有邮箱
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<String> listAllEmails();
}
