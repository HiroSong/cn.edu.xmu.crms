package cn.edu.xmu.crms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 14:48
 */
@RestController
@RequestMapping("/api")
public class CourseController {
    @GetMapping("/teachers/{teacherID}/courses")
    public Map<String, Object> getTeacherCourse(@PathVariable("teacherID")
                                                            String teacherID) {
        return  null;
    }
    @GetMapping("teachers/courses/{courseID}")
    public Map<String, Object> getCourseInfo(@PathVariable("courseID")
                                             String courseID) {
        return null;
    }
    @GetMapping("/students/{studentID}/courses")
    public Map<String, Object> getStudentCourse(@PathVariable("studentID")
                                                        String studentID) {
        return  null;
    }
}
