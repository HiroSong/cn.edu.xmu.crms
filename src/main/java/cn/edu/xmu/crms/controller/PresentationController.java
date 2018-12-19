package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/30 14:56
 */
@RestController
@RequestMapping("/api")
public class PresentationController {
    @Autowired
    SeminarService seminarService;

    @GetMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams/{teamID}")
    public Map<String, Object> registSeminar(@PathVariable("seminarID")
                                                     BigInteger seminarID,
                                             @PathVariable("classID")
                                                     BigInteger classID,
                                             @PathVariable("teamID")
                                                     BigInteger teamID,
                                             @RequestBody Integer presentationOrder){
        return null;
    }
    @PostMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams/{teamID}")
    public void changeRegistion(@PathVariable("seminarID") String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID,
                                @RequestBody Integer presentationOrder){
    }

    @DeleteMapping("/seminars/{seminarID}/classes/{classID}" +
            "/presentations/teams/{teamID}")
    public void cancelRegistion(@PathVariable("seminarID")
                                        String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID){
    }
    @GetMapping("/student/seminars/{seminarID}/classes/{classID}/presentations")
    public Map<String, Object> getPresentationInfo(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("classID") BigInteger classID){
        return null;
    }
}
