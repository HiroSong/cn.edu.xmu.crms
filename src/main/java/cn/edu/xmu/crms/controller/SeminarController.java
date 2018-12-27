package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.service.SeminarService;
import cn.edu.xmu.crms.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


/**
 * @ClassName SeminarController
 * @Author Hongqiwu
 * @Date 2018/12/20 2:06
 **/
@RestController
public class SeminarController {
    @Autowired
    SeminarService seminarService;

    @PostMapping("/seminar")
    public Map<String, Object> createSeminar(@RequestBody Map<String,Object> newSeminar) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Seminar seminar = new Seminar();
        seminar.setSeminarName(newSeminar.get("topic").toString());
        seminar.setIntroduction(newSeminar.get("intro").toString());
        seminar.setSeminarSerial(Integer.parseInt(newSeminar.get("order").toString()));
        seminar.setRoundOrder(Integer.parseInt(newSeminar.get("roundOrder").toString()));
        seminar.setBeVisible(Integer.parseInt(newSeminar.get("visible").toString()));
        seminar.setMaxTeam(Integer.parseInt(newSeminar.get("teamNumLimit").toString()));
        seminar.setEnrollStartTime(Timestamp.valueOf(newSeminar.get("signUpStartTime").toString()));
        seminar.setEnrollEndTime(Timestamp.valueOf(newSeminar.get("signUpEndTime").toString()));
        seminar.setCourseID(new BigInteger(newSeminar.get("courseID").toString()));
        return seminarService.insertNewSeminar(seminar);
    }

    @GetMapping("/seminar/{seminarID}/class")
    public List<Map<String, Object>> listKlassInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        return seminarService.listKlassInfoBySeminarID(seminarID);
    }

    @PutMapping("/seminar/{seminarID}")
    public void modifySeminarInfo(@PathVariable("seminarID") BigInteger seminarID,
                                  @RequestBody Map<String,Object> newSeminar) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Seminar seminar = new Seminar();
        seminar.setID(new BigInteger(newSeminar.get("id").toString()));
        seminar.setSeminarName(newSeminar.get("topic").toString());
        seminar.setIntroduction(newSeminar.get("intro").toString());
        seminar.setSeminarSerial(Integer.parseInt(newSeminar.get("order").toString()));
        seminar.setBeVisible(Integer.parseInt(newSeminar.get("visible").toString()));
        seminar.setEnrollStartTime(Timestamp.valueOf(newSeminar.get("signUpStartTime").toString()));
        seminar.setEnrollEndTime(Timestamp.valueOf(newSeminar.get("signUpEndTime").toString()));
        seminar.setMaxTeam(Integer.parseInt(newSeminar.get("teamNumLimit").toString()));
        seminarService.updateSeminarInfoBySeminar(seminar);
    }

    @DeleteMapping("/seminar/{seminarID}")
    public void deleteSeminar(@PathVariable("seminarID") BigInteger seminarID) {
        seminarService.deleteSeminarInfoBySeminarID(seminarID);
    }

    @GetMapping("/seminar/{seminarID}")
    public Map<String, Object> getSeminarInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        return seminarService.getSeminarInfoBySeminarID(seminarID);
    }

    //教师修改讨论课报告截止时间
    @PutMapping("/seminar/{seminarID}/class/{classID}/reportddl")
    public void modifySeminarReportInfo(@PathVariable("seminarID") BigInteger seminarID,
                                  @PathVariable("classID") BigInteger klassID,
                                  @RequestBody Map<String,Timestamp> reportDDL) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Map<String, Object> map = new HashMap<>(3);
        map.put("seminarID",seminarID);
        map.put("klassID",klassID);
        map.put("reportDDL",reportDDL.get("reportDDL"));
        seminarService.updateSeminarReportDDLByKlassAndSeminarID(map);
    }

    @GetMapping("/seminar/{seminarID}/class/{classID}")
    public Map<String, Object> getSeminarInfoBySeminarAndKlassID(@PathVariable("seminarID") BigInteger seminarID,
                                                                 @PathVariable("classID") BigInteger klassID) {
        return seminarService.getSeminarInfoBySeminarAndKlassID(seminarID,klassID);
    }

    //教师开始讨论课
    @PutMapping("/seminar/{seminarID}/class/{classID}/status")
    public void startSeminar(@PathVariable("seminarID") BigInteger seminarID,
                             @PathVariable("classID") BigInteger klassID) {
        seminarService.updateSeminarStatus(klassID,seminarID);
    }

    //获得某次讨论课某个队伍的成绩
    @GetMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String, Object> getTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                               @PathVariable("teamID") BigInteger teamID) {
        return seminarService.getSeminarScoreBySeminarAndTeamID(seminarID, teamID);
    }

    //修改某次讨论课某队伍成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String, Object> modifyTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                      @PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody Map<String, Object> map) {
        return seminarService.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,map);
    }

    //修改某次讨论课某队伍展示成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/presentationscore")
    public Map<String, Object> modifyPreScore(@PathVariable("seminarID") BigInteger seminarID,
                                              @PathVariable("teamID") BigInteger teamID,
                                              @RequestBody Map<String, Object> preScore){
        return seminarService.updatePresentationScoreBySeminarAndTeamID(seminarID,teamID,preScore);
    }

    //修改某次讨论课某队伍提问成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/questionscore")
    public Map<String, Object> modifyQuestionScore(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("teamID") BigInteger teamID,
                                                   @RequestBody Map<String, Object> quesScore){
        return seminarService.updateQuestionScoreBySeminarAndTeamID(seminarID,teamID,quesScore);
    }

    //修改某次讨论课某队伍报告成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/reportscore")
    public Map<String, Object> modifyReportScore(@PathVariable("seminarID") BigInteger seminarID,
                                                 @PathVariable("teamID") BigInteger teamID,
                                                 @RequestBody Map<String, Object> repScore){
        return seminarService.updateReportScoreBySeminarAndTeamID(seminarID,teamID,repScore);
    }

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
    public Map<String,String> cancelRegistion(@PathVariable("attendanceID")
                                        BigInteger attendanceID){
        return seminarService.cancelRegistion(attendanceID);
    }

    @GetMapping("/seminar/{seminarID}/team/{teamID}/attendance")
    public Map<String, Object> checkIfAttendance(@PathVariable("seminarID") BigInteger seminarID,
                                                 @PathVariable("teamID") BigInteger teamID) {
        return seminarService.checkIfAttendanceBySeminarIDAndTeamID(seminarID,teamID);
    }

    /*@GetMapping("/user/information")
    public void send()
    {
        Email email=new Email();
        email.sendSimpleMail("2737785072@qq.com","123456");
    }*/
//test email

}
