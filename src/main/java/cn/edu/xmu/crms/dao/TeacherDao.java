package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;


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

    public Boolean insertTeacher(Teacher teacher) {
        List<String> emails = teacherMapper.listAllEmails();
        for(int i = 0; i < emails.size(); i++) {
            if(teacher.getEmail().equals(emails.get(i))) {
                return false;
            }
        }
        System.out.println(teacherMapper.insertTeacher(teacher));
        return true;
    }

    public List<Teacher> listAllTeachers() {
        return teacherMapper.listAllTeachers();
    }

    public Integer updateTeacherInfoByTeacher(Teacher teacher) {
        return teacherMapper.updateTeacherInfoByTeacher(teacher);
    }

    public Integer deleteTeacherByTeacherID(BigInteger teacherID) {
        return teacherMapper.deleteTeacherByTeacherID(teacherID);
    }

    public Integer updateTeacherActiveByTeacher(Teacher teacher) {
        return teacherMapper.updateTeacherActiveByTeacher(teacher);
    }

    public Integer resetTeacherPasswordByTeacherID(BigInteger teacherID) {
        return teacherMapper.resetTeacherPasswordByTeacherID(teacherID);
    }

}
