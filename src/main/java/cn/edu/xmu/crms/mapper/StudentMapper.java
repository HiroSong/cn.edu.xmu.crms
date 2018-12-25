package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.math.BigInteger;
import java.util.List;


/**
 * @ClassName StudentMapper
 * @Description 有关数据库中学生信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
public interface StudentMapper {

    /**
     * 通过学生id获取学生对象
     *
     * @param studentID 学号
     * @return Student 学生对象
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Student getStudentByStudentID(BigInteger studentID);

    /**
     * 用于通过courseID和teamID获取学生ID列表
     *
     * @param courseID 课程号
     * @param teamID 队伍ID
     * @return 学生ID列表
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<BigInteger> listStudentsIDByCourseAndTeamID(BigInteger courseID, BigInteger teamID);
    /**
     * 用于通过courseID获取未组队学生ID列表
     *
     * @param courseID 课程ID
     * @return 未组队学生ID列表
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<BigInteger> listNoTeamStudentsIDByCourseID(BigInteger courseID);

    /**
     * 获取所有学生对象
     *
     * @return 所有学生
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<Student> listAllStudents();

    /**
     * 用于修改学生信息
     *
     * @param  student 学生对象
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer updateStudentInfoByStudent(Student student);

    /**
     * 用于重置学生密码为123456
     *
     * @param  studentID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer resetStudentPasswordByStudentID(BigInteger studentID);

    /**
     * 用于删除单个学生
     *
     * @param  studentID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer deleteStudentByStudentID(BigInteger studentID);

    /**
     * 用于激活某个学生账号
     *
     * @param  student 学生账号
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Integer updateStudentActiveByStudent(Student student);

    /**
     * 通过teamID获得leaderID
     *
     * @param teamID 队伍ID
     * @return BigInteger leaderID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger getLeaderIDByTeamID(BigInteger teamID);
    /**
      *  通过学号获得学生对象
      *
      * @param username 学号
      * @return Student student
      * @author SongLingbing
      * @date 2018/12/23 21:53
      */
    Student getStudentByStudentAccount(String username);

    /**
      * 创建用户
      *
      * @param user 用户信息
      * @return void
      * @author SongLingbing
      * @date 2018/12/24 10:58
      */
    void insertStudent(User user);

}
