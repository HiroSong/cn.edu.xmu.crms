package cn.edu.xmu.crms.controller;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.service.SeminarService;
import cn.edu.xmu.crms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:02
 */
@RestController
@RequestMapping("/api")
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    SeminarService seminarService;
    @GetMapping("/teachers/courses/{courseID}/teams")
    public Map<String,Object> getTeamList(@PathVariable("courseID")
                                                      String courseID){
        BigInteger courseId=new BigInteger(courseID);
        Map<String,Object> resultTeams=teamService.getTeamListByCourseId(courseId);
        if(null==resultTeams)
            return null;
        else return resultTeams;
    }
    @GetMapping("/teachers/seminars/{seminarID}/classes/" +
            "{classID}/teams")
    public Map<String, Object> getSeminarTeam(@PathVariable("seminarID")
                                              String seminarID,
                                              @PathVariable("classID")
                                              String classID){
        BigInteger seminarId=new BigInteger(seminarID);
        BigInteger classId=new BigInteger(classID);
        Seminar seminar=seminarService.getSeminarBySeminarIdAndClassId(seminarId,classId);

        return null;
    }
    @GetMapping("/students/{studentID}/courses/{courseID}/teams")
    public Map<String, Object> getStudentTeam(@PathVariable("studentID")
                                              String studentID,
                                              @PathVariable("courseID")
                                              String courseID){
        BigInteger studentId=new BigInteger(studentID);
        BigInteger courseId=new BigInteger(courseID);
        Map<String,Object> map=teamService.getTeamByCourseIdAndStudentId(courseId,studentId);
        if(null==map)
            return null;
        else return map;
    }
}
