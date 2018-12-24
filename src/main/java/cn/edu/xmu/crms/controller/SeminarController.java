package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;


/**
 * @author SongLingbing
 * @date 2018/11/29 15:32
 */
@RestController
@RequestMapping("/api")
public class SeminarController {

    @Autowired
    SeminarService seminarService;

    @GetMapping("/course/{courseID}/class/{classID}/seminar")
    public Map<String, Object> getSeminarInProgress(@PathVariable("courseID") BigInteger courseID,
                                                    @PathVariable("classID") BigInteger classID) {
        return seminarService.getSeminarInProgressByCourseIDAndClassID(courseID,classID);
    }

    @GetMapping("/seminar/{seminarID}/team/{teamID}/attendance")
    public Map<String, Object> checkIfAttendance(@PathVariable("seminarID") BigInteger seminarID,
                                                    @PathVariable("teamID") BigInteger teamID) {
        return seminarService.checkIfAttendanceBySeminarIDAndTeamID(seminarID,teamID);
    }

}
