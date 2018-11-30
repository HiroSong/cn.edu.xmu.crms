package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:18
 */
@RestController
@RequestMapping("/api")
public class StudentController {
    @GetMapping("/{studentID}")
    public Teacher getBaseInfo(@PathVariable("studentID")
                                       String studentID){
        return null;
    }

}
