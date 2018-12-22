package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.service.StudentService;
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

    @PostMapping("/teacher")
    public void createTeacher(@RequestBody Teacher teacher) {

    }

}
