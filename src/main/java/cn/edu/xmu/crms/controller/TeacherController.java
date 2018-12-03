package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Teacher;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

//import javax.jws.soap.SOAPBinding;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:17
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    @GetMapping("/{teacherID}")
    public Teacher getBaseInfo(@PathVariable("teacherID")
                                               String teacherID){
        return null;
    }

}
