package cn.edu.xmu.crms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:02
 */
@RestController
@RequestMapping("/api")
public class TeamController {
    @GetMapping("/teachers/courses/{courseID}/teams")
    public Map<String,Object> getTeamList(@PathVariable("courseID")
                                                      String courseID){
        return null;
    }
    @GetMapping("/teachers/seminars/{seminarID}/classes/" +
            "{classID}/teams")
    public Map<String, Object> getSeminarTeam(@PathVariable("seminarID")
                                              String seminarID,
                                              @PathVariable("classID")
                                              String classID){
        return null;
    }
    @GetMapping("/students/{studentID}/courses/{courseID}/teams")
    public Map<String, Object> getStudentTeam(@PathVariable("studentID")
                                              String studentID,
                                              @PathVariable("courseID")
                                              String courseID){
        return null;
    }
}
