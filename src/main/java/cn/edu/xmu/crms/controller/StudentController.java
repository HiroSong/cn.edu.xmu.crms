package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/12/01 15:02
 */
@RestController
@RequestMapping("/api")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/students/{studentID}")
    public Student getBaseInfo(@PathVariable("studentID")
                                       String studentID){
        BigInteger id = new BigInteger(studentID);
        Student student = studentService.getStudentByStudentId(id);
        if(student == null) {
            return null;
        }
        return student;
    }
}
