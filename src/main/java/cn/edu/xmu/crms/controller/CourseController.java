package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.service.*;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
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
    @Autowired
    TeamShareService teamShareService;
    @Autowired
    SeminarShareService seminarShareService;
    @Autowired
    RoundService roundService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/course")
    public List<Map<String, Object>> listCoursesInfo(HttpServletRequest request) {
        BigInteger id = jwtTokenUtil.getIDFromRequest(request);
        System.out.println(jwtTokenUtil.getRolesFromRequest(request));
        return courseService.listCoursesInfoByStudentOrTeacherID(id);
    }

    @GetMapping("/course/{courseID}/round")
    public List<Map<String, Object>> listRoundsInfoByCourseID(@PathVariable("courseID")
                                                              BigInteger courseID) {
        return roundService.listRoundsInfoByCourseID(courseID);
    }

    @PreAuthorize("hasAuthority('student')")
    @GetMapping("/course/{courseID}")
    public Map<String, Object> getCourseInfoByCourseID(@PathVariable("courseID")
                                                       BigInteger courseID) {
       return courseService.getCourseInfoByCourseID(courseID);
    }

    @PreAuthorize("hasAuthority('teacher')")
    @DeleteMapping("/course/{courseID}")
    public void deleteCourseByCourseID(@PathVariable("courseID")
                                                   BigInteger courseID) {
        courseDao.deleteCourseInfoByCourseID(courseID);
    }

    @PostMapping("/course")
    public BigInteger createNewCourse(HttpServletRequest request, @RequestBody Course course) {
        BigInteger teacherID = jwtTokenUtil.getIDFromRequest(request);
        course.setTeacherID(teacherID);
        return courseService.createNewCourse(course);
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
        return teamShareService.listMainAndSubCoursesInfoByCourseID(courseID);
    }

    @GetMapping("/course/{courseID}/seminarshare")
    public List<Map<String, Object>> listAllSeminarShareByCourseID(@PathVariable("courseID")
                                                                        BigInteger courseID) {
        return seminarShareService.listMainAndSubCoursesInfoByCourseID(courseID);
    }

    @DeleteMapping("/course/teamshare/{teamShareID}")
    public void deleteTeamShareByTeamShareID(@PathVariable("teamShareID")
                                               BigInteger teamShareID) {
        teamShareService.deleteTeamShareByTeamShareID(teamShareID);
    }

    @DeleteMapping("/course/seminarshare/{seminarShareID}")
    public void deleteSeminarShareBySeminarShareID(@PathVariable("seminarShareID")
                                                     BigInteger seminarShareID) {
        seminarShareService.deleteSeminarShareBySeminarShareID(seminarShareID);
    }

    @PostMapping("/course/{courseID}/teamsharerequest")
    public BigInteger createTeamShareRequestByCourseID(@PathVariable("courseID") BigInteger mainCourseID,
                                                       @RequestBody Map<String, BigInteger> subCourseID) {
        return teamShareService.createTeamShareRequestByCourseID(mainCourseID, subCourseID.get("subCourseID"));
    }

    @PostMapping("/course/{courseID}/seminarsharerequest")
    public BigInteger createSeminarShareRequestByCourseID(@PathVariable("courseID") BigInteger mainCourseID,
                                                       @RequestBody Map<String, BigInteger> subCourseID) {
        return seminarShareService.createSeminarShareRequestByCourseID(mainCourseID,subCourseID.get("subCourseID"));
    }

    @PostMapping("/course/{courseID}/class ")
    public BigInteger createNewKlass(@PathVariable("courseID") BigInteger courseID,
                                                          @RequestBody Klass klass) {
        klass.setCourseID(courseID);
        return klassService.createNewKlass(klass);
    }
}
