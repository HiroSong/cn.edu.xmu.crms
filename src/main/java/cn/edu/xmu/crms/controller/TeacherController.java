package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.service.StudentService;
import cn.edu.xmu.crms.service.TeacherService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Hongqiwu
 * @date 2018/12/01 15:02
 */
@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @PostMapping("/teacher")
    public Map<String, Object> createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @GetMapping("/teacher")
    public List<Map<String, Object>> listAllTeachersInfo() {
        return teacherService.listAllTeachersInfo();
    }

    @PutMapping("/teacher/{teacherID}/information")
    public Map<String, Object> modifyTeacherInfo(@PathVariable("teacherID") BigInteger teacherID,
                                                 @RequestBody Teacher teacher) {
        teacher.setID(teacherID);
        return teacherService.updateTeacherInfoByTeacherID(teacher);
    }

    @PutMapping("/teacher/{teacherID}/password")
    public Map<String, Object> resetTeacherPassword(@PathVariable("teacherID") BigInteger teacherID) {
        return teacherService.resetTeacherPasswordByTeacherID(teacherID);
    }

    @DeleteMapping("/teacher/{teacherID}")
    public void deleteTeacher(@PathVariable("teacherID") BigInteger teacherID) {
        teacherService.deleteTeacherByTeacherID(teacherID);
    }

    @PutMapping("/teacher/active")
    public Map<String, Object> activateStudent(@RequestBody Teacher teacher) {
        return teacherService.updateTeacherActiveByTeacherID(teacher);
    }
}
