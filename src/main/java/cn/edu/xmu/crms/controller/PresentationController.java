package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author LaiShaopeng
 * @date 2018/12/24 17:36
 */
@RestController
@RequestMapping("/api")
public class PresentationController {
    @Autowired
    SeminarService seminarService;
    @PostMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public Map<String, Object> registerSeminar(@PathVariable("seminarID")
                                                     BigInteger seminarID,
                                             @PathVariable("classID")
                                                     BigInteger classID,
                                             @RequestBody Map<String,Object> teamIDAndOrder){
        BigInteger teamID=new BigInteger(teamIDAndOrder.get("teamID").toString());
        Integer teamOrder=Integer.parseInt(teamIDAndOrder.get("teamOrder").toString());
        return seminarService.createNewAttendance(seminarID,classID,teamID,teamOrder);
    }
    @DeleteMapping("/attendance/{attendanceID}")
    public void cancelRegistion(@PathVariable("attendanceID")
                                        BigInteger attendanceID){
        seminarService.deleteAttendance(attendanceID);
    }

    @GetMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public List<Map<String, Object>> listAttendancesInfo(@PathVariable("seminarID") BigInteger seminarID,
                                                         @PathVariable("classID") BigInteger classID){
        return seminarService.listAttendanceInfoBySeminarIDAndClassID(seminarID,classID);
    }

}
