package cn.edu.xmu.crms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:04
 */
@RestController
@RequestMapping("/api")
public class EduClassController {
    @GetMapping("/courses/{courseID}/classes")
    public Map<String, Object> getClassList(@PathVariable("courseID")
                                            String courseID){
        return null;
    }
}
