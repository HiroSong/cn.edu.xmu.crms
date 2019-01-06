package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
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
@RestController
@Service
public class TeacherService {

    @Autowired
    TeacherDao teacherDao;

    @PostMapping("/teacher")
    public Boolean createTeacher(@RequestBody Teacher teacher) {
        return teacherDao.insertTeacher(teacher);
    }

    @GetMapping("/teacher/{teacherID}")
    public Teacher getTeacherInfo(@PathVariable("teacherID") BigInteger teacherID) {
        return teacherDao.getTeacherByTeacherID(teacherID);
    }

    @GetMapping("/teacher")
    public List<Map<String, Object>> listAllTeachersInfo() {
        List<Teacher> teachers = teacherDao.listAllTeachers();
        List<Map<String, Object>> teacherInfoList = new ArrayList<>();
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            Map<String, Object> map = new HashMap<>(4);
            map.put("id",teacher.getID());
            map.put("account",teacher.getUsername());
            map.put("name",teacher.getName());
            map.put("email",teacher.getEmail());
            teacherInfoList.add(map);
        }
        return teacherInfoList;
    }


    @PutMapping("/teacher/{teacherID}/information")
    public Teacher modifyTeacherInfo(@PathVariable("teacherID") BigInteger teacherID,
                                     @RequestBody Teacher teacher) {
        teacher.setID(teacherID);
        if(teacherDao.updateTeacherInfoByTeacher(teacher) == 1) {
            return teacher;
        }
        return null;
    }


    @PutMapping("/teacher/{teacherID}/password")
    public Boolean resetTeacherPassword(@PathVariable("teacherID") BigInteger teacherID) {
        if(teacherDao.resetTeacherPasswordByTeacherID(teacherID) == 1) {
            return true;
        }
        return false;
    }


    @DeleteMapping("/teacher/{teacherID}")
    public Boolean deleteTeacher(@PathVariable("teacherID") BigInteger teacherID) {
        if(teacherDao.deleteTeacherByTeacherID(teacherID) == 1) {
            return true;
        }
        return false;
    }


    @PutMapping("/teacher/active")
    public Boolean activeTeacher(@RequestBody Teacher teacher) {
        if(teacherDao.updateTeacherActiveByTeacher(teacher) == 1) {
            return true;
        }
        return false;
    }

}
