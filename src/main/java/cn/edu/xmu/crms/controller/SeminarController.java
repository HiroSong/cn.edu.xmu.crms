package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.SeminarDao;
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
    SeminarDao seminarDao;
    @Autowired
    SeminarService seminarService;
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
    /**
     * 修改讨论课状态为正在进行
     * @author Hongqiwu
     * @date 2018/12/03 21:53
     */
    @PutMapping("/seminars/{seminarID}/classes/{classID}")
    public Integer startSeminar(@PathVariable("seminarID") BigInteger seminarID,
                             @PathVariable("classID") BigInteger classID) {
        seminarDao.updateSeminarState(seminarID, classID);
        Integer result = seminarDao.getSeminarStateBySeminarAndClassID(seminarID, classID);
        return result;
    }
    /**
     * 删除讨论课
     * @author Hongqiwu
     * @date 2018/12/03 22:10
     */
    @DeleteMapping("/teachers/seminars/{seminarID}")
    public Boolean deleteSeminar(@PathVariable("seminarID") BigInteger seminarID) {
        seminarService.deleteSeminarAll(seminarID);
        if(seminarDao.getSeminarIDBySeminarID(seminarID) == null) {
            return true;
        }
        return false;
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
