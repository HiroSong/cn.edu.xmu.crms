package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getBaseInfo(@PathVariable("studentID")
                                       String studentID){
        BigInteger id = new BigInteger(studentID);
        Student student = studentService.getStudentByStudentId(id);
        if(student == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("studentName",student.getName());
        map.put("studentNumber",student.getNumber());
        map.put("email",student.getEmail());
        map.put("noticeGap",student.getNoticeGap());
        return map;
    }
}
