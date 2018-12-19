package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 12:35
 */
@Mapper
@Repository
public interface StudentDao {
    /**
      * 用于通过学生学号获取学生信息
      *
      * @param number 学生学号
      * @return Student 学生对象
      * @author SongLingbing
      * @date 2018/11/29 22:36
      */
    Student selectStudentByNumber(BigInteger number);
    /**
     * 用于通过学生id获取学生信息
     *
     * @param id 学号
     * @return Student 学生对象
     * @author hongqiwu
     * @date 2018/12/01 14:32
     */
    Student getStudentByStudentID(BigInteger id);
    /**
     * 用于通过班级号获取学生号列表
     *
     * @param id 班级号
     * @return ArrayList<> 学生号列表
     * @author Hongqiwu
     * @date 2018/12/01 14:32
     */
    List<BigInteger> listStudentIDByClassID(BigInteger id);
}
