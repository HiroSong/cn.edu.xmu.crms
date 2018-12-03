package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.service.SeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
                                                     String seminarID,
                                             @PathVariable("classID")
                                                     String classID,
                                             @PathVariable("teamID")
                                                     String teamID,
                                             @RequestBody Integer presentationOrder){
        BigInteger seminarId=new BigInteger(seminarID);
        BigInteger classId=new BigInteger(classID);
        BigInteger teamId=new BigInteger(teamID);
        /**
         * 获得讨论课信息
         * @author LaiShaopeng
         * @time 2018/12/3 8:25
         * */
        Seminar seminar=seminarService.getSeminarBySeminarIdAndClassId(seminarId,classId);
        if(null==seminar) return null;
        /**
         * 判断是否超过讨论课报名截止时间
         * @author LaiShaopeng
         * @time 2018/12/3 8:26
         * */
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=dateFormat.format(new Date());
        if(now.compareTo(seminar.getSeminarStartTime())>=0)
            return null;
        /**
         * 报名
         * @author LaiShaopeng
         * @time 2018/12/3 8:25
         * */
        if(seminarService.registSeminar(seminarId,teamId,presentationOrder))
        {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("PresentationOrder",presentationOrder);
            return map;
        }
        else return null;
    }
    @PostMapping("/seminars/{seminarID}/classes/{classID}/presentations/teams/{teamID}")
    public void changeRegistion(@PathVariable("seminarID") String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID,
                                @RequestBody Integer presentationOrder){
        BigInteger seminarId=new BigInteger(seminarID);
        BigInteger classId=new BigInteger(classID);
        BigInteger teamId=new BigInteger(teamID);

        /**
         * 获得讨论课信息
         * @author LaiShaopeng
         * @time 2018/12/3 13:00
         * */
        Seminar seminar=seminarService.getSeminarBySeminarIdAndClassId(seminarId,classId);
        if(null==seminar) return;
        /**
         * 判断是否超过讨论课报名截止时间
         * @author LaiShaopeng
         * @time 2018/12/3 13:00
         * */
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now=dateFormat.format(new Date());
        if(now.compareTo(seminar.getSeminarStartTime())>=0)
            return;

        seminarService.modifySeminarRegist(seminarId,teamId,presentationOrder);
    }

    @DeleteMapping("/seminars/{seminarID}/classes/{classID}" +
            "/presentations/teams/{teamID}")
    public void cancelRegistion(@PathVariable("seminarID")
                                        String seminarID,
                                @PathVariable("classID") String classID,
                                @PathVariable("teamID") String teamID){
        BigInteger seminarId=new BigInteger(seminarID);
        BigInteger classId=new BigInteger(classID);
        BigInteger teamId=new BigInteger(teamID);

        seminarService.cancelSeminarRegit(seminarId,teamId);
    }
}
