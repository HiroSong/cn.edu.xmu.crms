package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author Hongqiwu
 * @date 2018/11/30 20:09
 */
@RestController
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeamService teamService;
    @Autowired
    KlassService klassService;
    @Autowired
    SeminarService seminarService;

    @GetMapping("/course")
    public List<Map<String, Object>> listCoursesInfo() {
        BigInteger studentID = new BigInteger("0");
        //studentID是用jwt获取
        return courseService.listCoursesInfoByStudentID(studentID);
    }

    @GetMapping("/course/{courseID}")
    public Map<String, Object> getCourseInfoByCourseID(@PathVariable("courseID")
                                                       BigInteger courseID) {
       return courseService.getCourseInfoByCourseID(courseID);
    }

    @DeleteMapping("/course/{courseID}")
    public void deleteCourseByCourseID(@PathVariable("courseID")
                                                   BigInteger courseID) {
        courseDao.deleteCourseInfoByCourseID(courseID);
    }

    @PostMapping("/course")
    public BigInteger createNewCourse() {
        //teacherID等是用jwt获取
        //还缺少插入conflictCourses
        BigInteger teacherID = new BigInteger("0");
        String courseName = "";
        String introduction = "";
        Timestamp teamStartTime = new Timestamp(0);
        Timestamp teamEndTime = new Timestamp(0);
        Integer presentationPercentage = 0;
        Integer questionPercentage = 0;
        Integer reportPercentage = 0;
        Course newCourse = new Course();

        newCourse.setTeacherID(teacherID);
        newCourse.setCourseName(courseName);
        newCourse.setTeamStartTime(teamStartTime);
        newCourse.setTeamEndTime(teamEndTime);
        newCourse.setPresentationPercentage(presentationPercentage);
        newCourse.setQuestionPercentage(questionPercentage);
        newCourse.setReportPercentage(reportPercentage);
        BigInteger courseID = courseDao.insertCourseByCourse(newCourse);
        return courseID;
    }

    @GetMapping("/course/{courseID}/team")
    public List<Map<String, Object>> listTeamsInfoByCourseID(@PathVariable("courseID")
                                                               BigInteger courseID) {
        return teamService.listTeamsInfoByCourseID(courseID);
    }

    @GetMapping("/course/{courseID}/myTeam")
    public Map<String, Object> getMyTeamInfoByCourseAndStudentID(@PathVariable("courseID")
                                                                     BigInteger courseID) {
        BigInteger studentID = new BigInteger("0");
        return teamService.getTeamInfoByCourseAndStudentID(courseID, studentID);
    }

    @GetMapping("/course/{courseID}/noTeam")
    public List<Map<String, Object>> listNoTeamStudentsInfoByCourseID(@PathVariable("courseID")
                                                                         BigInteger courseID) {
        return teamService.listNoTeamStudentsInfoByCourseID(courseID);
    }

    @GetMapping("/course/{courseID}/class")
    public List<Map<String, Object>> listKlassInfoByCourseID(@PathVariable("courseID")
                                                                              BigInteger courseID) {
        return klassService.listKlassInfoByCourseID(courseID);
    }

    @GetMapping("/course/{courseID}/teamshare")
    public List<Map<String, Object>> listAllTeamShareByCourseID(@PathVariable("courseID")
                                                                     BigInteger courseID) {
        return courseService.listMainAndSubCoursesInfoByCourseID(courseID);
    }

    @GetMapping("/course/{courseID}/seminarshare")
    public List<Map<String, Object>> listAllSeminarShareByCourseID(@PathVariable("courseID")
                                                                        BigInteger courseID) {
        return seminarService.listMainAndSubCoursesInfoByCourseID(courseID);
    }
}
