package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.service.*;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/course/{courseID}/round")
    public List<Map<String, Object>> listRoundsInfoByCourseID(@PathVariable("courseID")
                                                              BigInteger courseID) {
        return roundService.listRoundsInfoByCourseID(courseID);
    }


    @PreAuthorize("hasAuthority('teacher')")
    @DeleteMapping("/course/{courseID}")
    public void deleteCourseByCourseID(@PathVariable("courseID")
                                                   BigInteger courseID) {
        courseDao.deleteCourseInfoByCourseID(courseID);
    }



    @GetMapping("/course/{courseID}/teamshare")
    public List<Map<String, Object>> listAllTeamShareByCourseID(@PathVariable("courseID") BigInteger courseID) {
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

    @PostMapping("/course/{courseID}/class ")
    public BigInteger createNewKlass(@PathVariable("courseID") BigInteger courseID,
                                                          @RequestBody Klass klass) {
        Course course = new Course();
        course.setID(courseID);
        klass.setCourse(course);
        return klassService.createNewKlass(klass);
    }

    //创建队伍 三个不合法条件（还缺一个条件，选某门课程最少最多人数）
    @PostMapping("/course/{courseID}/team")
    public Map<String, Object> createNewTeam(@PathVariable("courseID") BigInteger courseID,
                                    @RequestBody Team team) {
        Course course = new Course();
        course.setID(courseID);
        team.setCourse(course);
        return teamService.createNewTeam(team);
    }
}
