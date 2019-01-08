package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.util.email.Email;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import cn.edu.xmu.crms.util.websocket.SeminarRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * @ClassName SeminarService
 * @Author Hongqiwu
 **/
@RestController
@Service
public class SeminarService {

    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    KlassDao klassDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    SeminarRoom seminarRoom;


    private Map<String, Object> getSeminarInfo(Seminar seminar) {
        Map<String, Object> map = new HashMap<>(7);
        map.put("id",seminar.getID());
        map.put("topic",seminar.getSeminarName());
        map.put("intro",seminar.getIntroduction());
        map.put("order",seminar.getSeminarSerial());
        map.put("teamNumLimit",seminar.getMaxTeam());
        map.put("signUpStartTime",seminar.getEnrollStartTime());
        map.put("signUpEndTime",seminar.getEnrollEndTime());
        map.put("round",seminar.getRound().getRoundSerial());
        map.put("beVisible",seminar.getBeVisible());
        map.put("roundID",seminar.getRound().getID());
        map.put("reportDDL",seminar.getReportDDL());
        return map;
    }

    @PreAuthorize("hasAuthority('teacher')")
    @GetMapping("/seminar/process")
    public Map<String, Object> getSeminarInProcess() {
        Seminar seminar = seminarDao.getSeminarInProcess();
        if(seminar==null){
            return null;
        }
        BigInteger klassID = seminarDao.getKlassIDByProcessSeminarID(seminar.getID());
        Map<String,Object> map = this.getSeminarInfo(seminar);
        map.put("klassID",klassID);
        return map;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/round/{roundID}/seminar")
    public List<Map<String, Object>> listSeminarsInfoByRoundID(@PathVariable("roundID") BigInteger roundID) {
        List<Map<String, Object>> seminarInfoList = new ArrayList<>();
        List<Seminar> seminars = seminarDao.listSeminarsByRoundID(roundID);
        for(int i = 0; i < seminars.size(); i++) {
            Seminar seminar = seminars.get(i);
            Map<String, Object> map = new HashMap<>(3);
            map.put("id",seminar.getID());
            map.put("topic",seminar.getSeminarName());
            map.put("order",seminar.getSeminarSerial());
            seminarInfoList.add(map);
        }
        return  seminarInfoList;
    }

    @PreAuthorize("hasAuthority('teacher')")
    @PostMapping("/course/{courseID}/seminar")
    public Map<String, Object> createSeminar(@PathVariable("courseID") BigInteger courseID,
                                             @RequestBody Seminar seminar) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Course course = new Course();
        course.setID(courseID);
        seminar.setCourse(course);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",seminarDao.insertSeminar(seminar));
        return map;
    }

    @PreAuthorize("hasAuthority('teacher')")
    @GetMapping("/seminar/{seminarID}/class")
    public List<Map<String, Object>> listKlassInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        List<Klass> klasses = klassDao.listKlassBySeminarID(seminarID);
        List<Map<String, Object>> klassInfoList = new ArrayList<>();
        for(int i = 0; i < klasses.size(); i++) {
            Klass klass = klasses.get(i);
            Map<String, Object> map = new HashMap<>(3);
            map.put("id",klass.getID());
            map.put("grade",klass.getGrade());
            map.put("serial",klass.getKlassSerial());
            klassInfoList.add(map);
        }
        return klassInfoList;
    }

    @PreAuthorize("hasAuthority('teacher')")
    @PutMapping("/seminar/{seminarID}")
    public void modifySeminarInfo(@PathVariable("seminarID") BigInteger seminarID,
                                  @RequestBody Seminar seminar) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        seminar.setID(seminarID);
        seminarDao.updateSeminarBySeminarID(seminar);
    }

    @PreAuthorize("hasAuthority('teacher')")
    @DeleteMapping("/seminar/{seminarID}")
    public void deleteSeminar(@PathVariable("seminarID") BigInteger seminarID) {
        seminarDao.deleteSeminarBySeminarID(seminarID);
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/seminar/{seminarID}")
    public Map<String, Object> getSeminarInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        Map<String,Object> map = new HashMap<>(0);
        Seminar seminar = seminarDao.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        return this.getSeminarInfo(seminar);
    }



    @GetMapping("/seminar/{seminarID}/class/{classID}/reportddl")
    public Map<String,Object> getSeminarReportInfo(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("classID") BigInteger klassID) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Map<String, Object> map = new HashMap<>(3);
        map.put("seminarID",seminarID);
        map.put("klassID",klassID);
        map.put("reportDDL",seminarDao.getReportDDLBySeminarAndKlassID(seminarID,klassID));
        return map;
    }


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
        seminarDao.updateSeminarReportDDLByKlassAndSeminarID(map);
    }


    @GetMapping("/seminar/{seminarID}/class/{classID}")
    public Map<String, Object> getSeminarInfoBySeminarAndKlassID(@PathVariable("seminarID") BigInteger seminarID,
                                                                 @PathVariable("classID") BigInteger klassID) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Seminar seminar = seminarDao.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        Map<String,Object> map = this.getSeminarInfo(seminar);
        Integer status = seminarDao.getStatusBySeminarAndKlassID(seminarID,klassID);
        map.put("status",status);
        String reportDDL = seminarDao.getReportDDLBySeminarAndKlassID(seminarID,klassID);
        if(reportDDL != null) {
            Timestamp ddl = Timestamp.valueOf(reportDDL);
            map.put("reportDDL",ddl);
        } else {
            map.put("reportDDL",null);
        }
        return map;
    }





    @PutMapping("/seminar/{seminarID}/class/{classID}/status")
    public void startOrEndSeminar(@PathVariable("seminarID") BigInteger seminarID,
                                  @PathVariable("classID") BigInteger klassID,
                                  @RequestBody Map<String,Integer> statusMap) {
        Integer status = statusMap.get("status");
        seminarDao.updateSeminarStatus(klassID,seminarID,status);
        if(status==1){
            BigInteger klassSeminarID = seminarDao.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
            seminarRoom=new SeminarRoom(klassSeminarID);
        }
    }


    @GetMapping("/seminar/{seminarID}/class/{classID}/enter")
    public void enterSeminar(@PathVariable("seminarID") BigInteger seminarID,
                                  @PathVariable("classID") BigInteger klassID) {
            BigInteger klassSeminarID = seminarDao.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
            if(!SeminarRoom.checkIfExistRoom(klassSeminarID)) {
                seminarRoom=new SeminarRoom(klassSeminarID);
            }
    }



    @GetMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String,Object> getTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                  @PathVariable("teamID") BigInteger teamID) {
        return seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
    }




    @PutMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String, Object> modifyTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                      @PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody Map<String, Object> map) {
        Map<String,Object> resultmap=seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,map);
        List<String> emailList = studentDao.listMemberEmailsByTeamID(teamID);
        for (String emailCount:emailList) {
            if(emailCount!=null){
                String emailContent="您的讨论课程成绩已更新。";
                Email email=new Email();
                email.sendSimpleMail(emailCount,emailContent);
            }
        }
        return resultmap;
    }




    @PutMapping("/seminar/{seminarID}/team/{teamID}/presentationscore")
    public Map<String, Object> modifyPreScore(@PathVariable("seminarID") BigInteger seminarID,
                                              @PathVariable("teamID") BigInteger teamID,
                                              @RequestBody Map<String, Object> preScore) {
        Double presentationScore = new Double(preScore.get("presentationScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.put("presentationScore",presentationScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }



    @PutMapping("/seminar/{seminarID}/team/{teamID}/questionscore")
    public Map<String, Object> modifyQuestionScore(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("teamID") BigInteger teamID,
                                                   @RequestBody Map<String, Object> quesScore){
        Double questionScore = new Double(quesScore.get("questionScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("questionScore",questionScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }




    @PutMapping("/seminar/{seminarID}/team/{teamID}/reportscore")
    public Map<String, Object> modifyReportScore(@PathVariable("seminarID") BigInteger seminarID,
                                                 @PathVariable("teamID") BigInteger teamID,
                                                 @RequestBody Map<String, Object> repScore){
        Double reportScore = new Double(repScore.get("reportScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("reportScore",reportScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    @PostMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public Map<String,Object> createNewAttendance(@PathVariable("seminarID")BigInteger seminarID,
                                                  @PathVariable("classID")BigInteger classID,
                                                  @RequestBody Map<String,Object> teamIDAndOrder) {
        BigInteger teamID=new BigInteger(teamIDAndOrder.get("teamID").toString());
        Integer teamOrder=Integer.parseInt(teamIDAndOrder.get("teamOrder").toString());
        BigInteger klassSeminarID = seminarDao.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Attendance attendance = teamDao.createAnAttendance(klassSeminarID,teamID,teamOrder);
        teamDao.insertAttendance(attendance);
        BigInteger attendanceID=teamDao.getLastInsertID();
        Map<String,Object> map=new HashMap<>(0);
        map.put("id",attendanceID);
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        return map;
    }

    @DeleteMapping("/attendance/{attendanceID}")
    public Map<String,String> cancelRegistion(@PathVariable("attendanceID")
                                                      BigInteger attendanceID){
        Map<String,String> map=new HashMap<>(0);
        if(teamDao.deleteAttendance(attendanceID)==0) {
            map.put("result","failure");
        } else{
            map.put("result","success");}
        return map;
    }

    @GetMapping("/seminar/{seminarID}/team/{teamID}/attendance")
    public Map<String,Object> checkIfAttendanceBySeminarIDAndTeamID(@PathVariable("seminarID") BigInteger seminarID,
                                                                    @PathVariable("teamID") BigInteger teamID){
        BigInteger klassID=klassDao.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        BigInteger klassSeminarID = seminarDao.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
        Attendance attendance=teamDao.getAttendanceByKlassSeminarIDAndTeamID(klassSeminarID,teamID);
        Map<String,Object> map=new HashMap<>(0);
        if(attendance == null) {
            return null;
        }
        map.put("id",attendance.getID());
        map.put("teamName",attendance.getTeam().getTeamName());
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        map.put("teamOrder",attendance.getTeamOrder());
        map.put("bePresent",attendance.getBePresent());
        map.put("pptName",attendance.getPPTName());
        map.put("pptUrl",attendance.getPPTUrl());
        map.put("reportName",attendance.getReportName());
        map.put("reportUrl",attendance.getReportUrl());
        return map;
    }
}
