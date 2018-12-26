package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.service.SeminarShareService;
import cn.edu.xmu.crms.service.TeamService;
import cn.edu.xmu.crms.service.TeamShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName RequestController
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/24 10:11
 **/
@RestController
public class RequestController {
    @Autowired
    TeamShareService teamShareService;
    @Autowired
    SeminarShareService seminarShareService;
    @Autowired
    TeamService teamService;

    @PutMapping("/request/teamshare/{teamShareID}")
    public Map<String, Object> updateTeamShareStatusByID(@PathVariable("teamShareID") BigInteger teamShareID,
                                                         @RequestBody Map<String,Object> statusMap) {
        Integer status = Integer.parseInt(statusMap.get("status").toString());
        return teamShareService.updateTeamShareStatusByID(teamShareID,status);
    }

    @PutMapping("/request/seminarshare/{seminarShareID}")
    public Map<String, Object> updateSeminarShareStatusByID(@PathVariable("seminarShareID") BigInteger seminarShareID,
                                                         @RequestBody Map<String,Object> statusMap) {
        Integer status = Integer.parseInt(statusMap.get("status").toString());
        return seminarShareService.updateSeminarShareStatusByID(seminarShareID,status);
    }

    @PostMapping("/request/teamshare")
    public Map<String, Object> createTeamShareRequest(@RequestBody Map<String,BigInteger> courseID) {
        BigInteger mainCourseID = courseID.get("mainCourseID");
        BigInteger subCourseID = courseID.get("subCourseID");
        return teamShareService.createTeamShareRequestByCourseID(mainCourseID, subCourseID);
    }

    @PostMapping("/request/seminarshare")
    public Map<String, Object> createSeminarShareRequest(@RequestBody Map<String,BigInteger> courseID) {
        BigInteger mainCourseID = courseID.get("mainCourseID");
        BigInteger subCourseID = courseID.get("subCourseID");
        return seminarShareService.createSeminarShareRequestByCourseID(mainCourseID, subCourseID);
    }

    @PutMapping("/request/teamvalid/{teamValidID}")
    public void updateTeamValidRequestByID(@PathVariable("teamValidID") BigInteger teamValidID,
                                           @RequestBody Map<String,Integer> map) {
        Integer status = map.get("handleType");
        teamService.updateTeamValidRequestByID(teamValidID,status);
    }
}
