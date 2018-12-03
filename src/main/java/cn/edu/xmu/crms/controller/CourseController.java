package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Deadline;
import cn.edu.xmu.crms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
        List<Course> course = courseService.getCourseByTeacherId(id);
        if(course == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(1);
        List<Map<String,Object>> courseList = new ArrayList<>();
        for(int i = 0; i < course.size(); i++) {
            Map<String, Object> courseMap = new HashMap<>(2);
            courseMap.put("courseID",course.get(i).getId());
            courseMap.put("courseName",course.get(i).getCourseName());
            courseList.add(courseMap);
        }
        map.put("courseList",courseList);
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
        Map<String, Object> map = new HashMap<>(5);
        Map<String, Object> scoreRule = new HashMap<>(3);
        Map<String, Object> teamLimit = new HashMap<>(2);
        scoreRule.put("presentationWeight",course.getPresentationWeight());
        scoreRule.put("questionWeight",course.getQuestionWeight());
        scoreRule.put("reportWeight",course.getReportWeight());
        teamLimit.put("maxNumber",course.getTeamMaxLimit());
        teamLimit.put("minNumber",course.getTeamMinLimit());
        map.put("courseRequire",course.getCourseRequire());
        map.put("scoreRule",scoreRule);
        map.put("teamLimit",teamLimit);
        map.put("teamStartTime",deadline.getBeginTime());
        map.put("teamEndTime",deadline.getEndTime());
        return map;
    }
    @GetMapping("/students/{studentID}/courses")
    public Map<String, Object> getStudentCourse(@PathVariable("studentID")
                                                        String studentID) {
        BigInteger id = new BigInteger(studentID);
        List<Course> course = courseService.getCourseByStudentId(id);
        if(course == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(1);
        List<Map<String,Object>> courseList = new ArrayList<>();
        for(int i = 0; i < course.size(); i++) {
            Map<String, Object> courseMap = new HashMap<>(2);
            courseMap.put("courseID",course.get(i).getId());
            courseMap.put("courseName",course.get(i).getCourseName());
            courseList.add(courseMap);
        }
        map.put("courseList",courseList);
        return  map;
    }
}
