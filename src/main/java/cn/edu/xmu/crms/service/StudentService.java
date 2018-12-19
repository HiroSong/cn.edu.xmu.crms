package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.Student;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/12/01 14:37
 */
public interface StudentService {
    /**
     * 用于根据学生号码查找学生并返回学生实例
     *
     * @param id 学生号码
     * @return Student 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/12/01 14:55
     */
    Student getStudentByStudentID(BigInteger id);
    /**
     * 用于根据班级号码查找学生并返回学生实例
     *
     * @param id 课程号码
     * @return ArrayList<> 返回查找到的对象，若无记录则为null
     * @author Hongqiwu
     * @date 2018/12/01 14:38
     */
    List<Student> getStudentByClassID(BigInteger id);
}
