package cn.edu.xmu.crms.controller;

import org.omg.CORBA.portable.ValueOutputStream;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:32
 */
@RestController
@RequestMapping("/api")
public class SeminarController {
    @GetMapping("/courses/{courseID}/seminars")
    public Map<String, Object> getRoundList(@PathVariable("courseID")
                                            String courseID){
        return null;
    }
    @GetMapping("/seminars/{seminarID}/classes/{classID}")
    public  Map<String, Object> getSeminarInfo(@PathVariable("seminarID")
                                               String seminarID,
                                               @PathVariable("classID")
                                               String classID){
        return null;
    }
    @PutMapping("/seminars/{seminarID}/classes/{classID}")
    public void startSeminar(@PathVariable("seminarID")
                                            String seminarID,
                                            @PathVariable("classID")
                                            String classID){
    }
    @DeleteMapping("/teachers/seminars/{seminarID}")
    public void deleteSeminar(@PathVariable("seminarID")
                                              String seminarID){
    }
    @GetMapping("/students/teams/{teamID}/registedSeminars")
    public Map<String, Object> getRegisted(@PathVariable("teamID")
                                           String teamID){
        return null;
    }

    @GetMapping("/students/teams/{teamID}/registedseminars/{seminarID}")
    public Map<String, Object> getRegistionInfo(@PathVariable("teamID")
                                 String teamID,
                                 @PathVariable("seminarID") String seminarID){
        return null;
    }
}
