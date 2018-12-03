package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/30 14:56
 */
@RestController
@RequestMapping("/api")
public class PresentationController {
    @GetMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams")
    public Map<String, Object> registSeminar(@PathVariable("seminarID")
                                                     String seminarID,
                                             @PathVariable("classID")
                                                     String classID){
        return null;
    }
    @PostMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams/{teamID}")
    public void changeRegistion(@PathVariable("seminarID") String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID,
                                @RequestBody Integer presentationOrder){
    }
    @DeleteMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams/{teamID}")
    public void cancleRegistion(@PathVariable("seminarID") String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID){

    }
    @GetMapping("/student/seminars/{seminarID}/classes/{classID}/presentations")
    public Map<String, Object> getPresentationInfo(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("classID") BigInteger classID){
        return null;
    }
}
