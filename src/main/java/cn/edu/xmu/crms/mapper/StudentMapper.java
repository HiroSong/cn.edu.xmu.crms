package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;


/**
 * @ClassName StudentMapper
 * @Description 有关数据库中学生信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface StudentMapper {
    /**
     * 用于通过学生id获取学生对象
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
     * @param courseID 课程号
     * @return 未组队学生ID列表
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<BigInteger> listNoTeamStudentsIDByCourseID(BigInteger courseID);
    /**
     * 用于获取所有学生的ID列表
     *
     * @return 所有学生ID列表
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    List<BigInteger> listAllStudentsID();
    /**
     * 用于修改学生信息
     *
     * @param  student 学生对象
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    void updateStudentInfoByStudent(Student student);
    /**
     * 用于重置学生密码为123456
     *
     * @param  studentID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    void resetStudentPasswordByStudentID(BigInteger studentID);
    /**
     * 用于删除单个学生
     *
     * @param  studentID 学生ID
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    void deleteStudentByStudentID(BigInteger studentID);
    /**
     * 用于激活某个学生账号
     *
     * @param  student 学生账号
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    void updateStudentActiveByStudentID(Student student);
}
