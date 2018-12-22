package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class TeacherDao {
    @Autowired
    TeacherMapper teacherMapper;

    public Teacher getTeacherByTeacherID(BigInteger teacherID) {
        return teacherMapper.getTeacherByTeacherID(teacherID);
    }

    public Teacher getTeacherByCourseID(BigInteger courseID) {
        BigInteger teacherID = teacherMapper.getTeacherIDByCourseID(courseID);
        return teacherMapper.getTeacherByTeacherID(teacherID);
    }

    public BigInteger insertTeacherByTeacher(Teacher teacher) {
        teacherMapper.insertTeacherByTeacher(teacher);
        return teacherMapper.getTeacherIDByAccountAndPassword(teacher.getAccount(), teacher.getPassword());
    }
}