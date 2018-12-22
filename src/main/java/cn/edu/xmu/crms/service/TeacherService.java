package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeacherService
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 4:05
 **/
@Service
public class TeacherService {
    @Autowired
    TeacherDao teacherDao;

    public Map<String, Object> createTeacher(Teacher teacher) {
        BigInteger teacherID = teacherDao.insertTeacherByTeacher(teacher);
        Map<String,Object> map = new HashMap<>(4);
        map.put("id",teacherID);
        map.put("account",teacher.getAccount());
        map.put("name",teacher.getName());
        map.put("email",teacher.getEmail());
        return map;
    }
}
