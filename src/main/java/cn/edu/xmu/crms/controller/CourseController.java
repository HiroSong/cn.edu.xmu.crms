package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Deadline;
import cn.edu.xmu.crms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hongqiwu
 * @date 2018/11/30 20:09
 */
@RestController
@RequestMapping("/api")
public class CourseController {
    @Autowired
    CourseService courseService;
    @GetMapping("/teachers/{teacherID}/courses")
    public Map<String, Object> getTeacherCourse(@PathVariable("teacherID")
                                                            String teacherID) {
        BigInteger id = new BigInteger(teacherID);
        ArrayList<Course> course = courseService.getCourseByTeacherId(id);
        if(course == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("courseList",course);
        return  map;
    }
    @GetMapping("/teachers/courses/{courseID}")
    public Map<String, Object> getCourseInfo(@PathVariable("courseID")
                                             String courseID) {
        BigInteger id = new BigInteger(courseID);
        Course course = courseService.getCourseByCourseId(id);
        Deadline deadline = courseService.getCourseDeadlineByCourseId(id);
        if(course == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(8);
        map.put("courseRequire",course.getCourseRequire());
        map.put("presentationWeight",course.getPresentationWeight());
        map.put("questionWeight",course.getQuestionWeight());
        map.put("reportWeight",course.getReportWeight());
        map.put("maxNumber",course.getTeamMaxLimit());
        map.put("minNumber",course.getTeamMinLimit());
        map.put("teamStartTime",deadline.getBeginTime());
        map.put("teamEndTime",deadline.getEndTime());
        return map;
    }
    @GetMapping("/students/{studentID}/courses")
    public Map<String, Object> getStudentCourse(@PathVariable("studentID")
                                                        String studentID) {
        BigInteger id = new BigInteger(studentID);
        ArrayList<Course> course = courseService.getCourseByStudentId(id);
        if(course == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("courseList",course);
        return  map;
    }
}
