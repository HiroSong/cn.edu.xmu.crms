package cn.edu.xmu.crms.controller;


import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.service.*;

import java.math.BigInteger;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author SongLingbing
 * @date 2018/11/29 15:03
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;
    @GetMapping("/user/information")
    public Map<String, Object> getUserInfoByUserID(@PathVariable("userID")
                                                                   BigInteger userID) {
        BigInteger id = new BigInteger("0");
        //如果是学生
        if(id.equals(0))
            return studentService.getStudentInfoByStudentID(id);
        else //id是老师
            return teacherService.getTeacherInfoByTeacherID(id);
    }
    @PutMapping("/user/email")
    public Map<String, Object> modifyUseremailByUserID(@PathVariable("userID") BigInteger userID ,
                                                       @RequestBody User user) {
        BigInteger id = new BigInteger("0");
        //jwt判断id是老师还是学生
        if(id.equals(0)) {
            user.setID(userID);
            return userService.updateStudentInfoByStudent(user);
        }
        else //id是老师
            return userService.updateTeacherInfoByTeacher(id,userEmail);
    }


}
