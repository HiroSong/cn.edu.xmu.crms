package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:17
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @GetMapping("/{teacherID}")
    public Teacher getBaseInfo(@PathVariable("teacherID")
                                               String teacherID){
        BigInteger id = new BigInteger(teacherID);
        Teacher teacher = teacherService.getTeacherByTeacherId(id);
        if(teacher == null) {
            return null;
        }
        return teacher;
    }

}
