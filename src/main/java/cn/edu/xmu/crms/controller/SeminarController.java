package cn.edu.xmu.crms.controller;

//import org.omg.CORBA.portable.ValueOutputStream;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.HashMap;
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
    @GetMapping("/courses/{courseID}/seminars")
    public Map<String, Object> getRoundList(@PathVariable("courseID")
                                            String courseID){
        BigInteger id=new BigInteger(courseID);
        List<Round> round=seminarService.getRoundByCourseID(id);
        if(round==null){
            return null;
        }
        Map<String, Object>map=new HashMap<>(1);
        map.put("roundList",round);
        return map;
    }
    @GetMapping("/seminars/{seminarID}/classes/{classID}")
    public  Map<String, Object> getSeminarInfo(@PathVariable("seminarID")
                                               String seminarID,
                                               @PathVariable("classID")
                                               String classID){
        BigInteger seminarid=new BigInteger(seminarID);
        BigInteger classid=new BigInteger(classID);
        Seminar seminar = seminarService.getSeminarBySeminarIdAndClassId(seminarid,classid);
        if(seminar == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(7);
        map.put("roundNumber",seminar.getRound());
        map.put("seminarNumber",seminar.getSeminarName());
        map.put("seminarOrder",seminar.getRegistOrder());
        map.put("seminarRequire",seminar.getSeminarRequire());
        map.put("seminarState",seminar.getSeminarState());
        map.put("registStartTime",seminar.getSeminarStartTime());
        map.put("registEndTime",seminar.getTeamLimit());
        return map;
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
