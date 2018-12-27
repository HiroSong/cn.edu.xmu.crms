package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.StudentMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
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
    @Autowired
    TeacherMapper teacherMapper;

    public Map<String, Object> createTeacher(Teacher teacher) {
        BigInteger teacherID = teacherDao.insertTeacherByTeacher(teacher);
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", teacherID);
        map.put("account", teacher.getAccount());
        map.put("name", teacher.getName());
        map.put("email", teacher.getEmail());
        return map;
    }

    public List<Map<String, Object>> listAllTeachersInfo() {
        List<Teacher> teachers = teacherDao.listAllTeachers();
        List<Map<String, Object>> teacherInfoList = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            Map<String, Object> map = new HashMap<>(4);
            map.put("id", teacher.getID());
            map.put("account", teacher.getAccount());
            map.put("name", teacher.getTeacherName());
            map.put("email", teacher.getEmail());
            teacherInfoList.add(map);
        }
        return teacherInfoList;
    }

    public Map<String, Object> updateTeacherInfoByTeacherID(Teacher teacher) {
        teacherMapper.updateTeacherInfoByTeacherID(teacher);
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", teacher.getID());
        map.put("account", teacher.getAccount());
        map.put("name", teacher.getName());
        map.put("email", teacher.getEmail());
        return map;
    }

    public Map<String, Object> resetTeacherPasswordByTeacherID(BigInteger teacherID) {
        Map<String, Object> map = new HashMap<>(4);
        teacherMapper.resetTeacherPasswordByTeacherID(teacherID);
        Teacher teacher = teacherMapper.getTeacherByTeacherID(teacherID);
        map.put("id", teacher.getID());
        map.put("account", teacher.getAccount());
        map.put("name", teacher.getTeacherName());
        map.put("email", teacher.getEmail());
        return map;
    }

    public void deleteTeacherByTeacherID(BigInteger teacherID) {
        teacherMapper.deleteTeacherByTeacherID(teacherID);
    }

    public Map<String, Object> updateTeacherActiveByTeacherID(Teacher teacher) {
        teacherMapper.updateTeacherActiveByTeacherID(teacher);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", teacher.getID());
        return map;
    }

    public Map<String, Object> getTeacherInfoByTeacherID(BigInteger teacherID) {
        Map<String, Object> map = new HashMap<>(3);
        Teacher teacher = teacherMapper.getTeacherByTeacherID(teacherID);
        map.put("name", teacher.getTeacherName());
        map.put("account", teacher.getAccount());
        map.put("email", teacher.getEmail());
        return map;
    }

}
