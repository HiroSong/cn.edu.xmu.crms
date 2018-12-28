package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
/*import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIGlobalBinding;*/
import cn.edu.xmu.crms.util.websocket.GreetingController;
import org.springframework.beans.factory.annotation.Autowired;
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
    SeminarMapper seminarMapper;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    RoundMapper roundMapper;
    @Autowired
    KlassDao klassDao;
    @Autowired
    KlassMapper klassMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    QuestionDao questionDao;

GreetingController greetingController;

    //获取seminar信息
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
        return map;
    }



    
    


    //未完成
    public List<Map<String, Object>> listSeminarScores(BigInteger studentID, BigInteger courseID) {
        List<Map<String, Object>> listScoresInfo = new ArrayList<>();
        return listScoresInfo;
    }

    @GetMapping("/round/{roundID}/seminar")//获得某轮下的讨论课信息
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

    public List<Map<String, Object>> listKlassInfoBySeminarID(BigInteger seminarID) {
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



    @PutMapping("/seminar/{seminarID}")
    public void modifySeminarInfo(@PathVariable("seminarID") BigInteger seminarID,
                                  @RequestBody Seminar seminar) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        seminar.setID(seminarID);
        seminarDao.updateSeminarBySeminarID(seminar);
    }


    @DeleteMapping("/seminar/{seminarID}")
    public void deleteSeminar(@PathVariable("seminarID") BigInteger seminarID) {
        seminarDao.deleteSeminarBySeminarID(seminarID);
    }


    @GetMapping("/seminar/{seminarID}")//获取单个讨论课信息
    public Map<String, Object> getSeminarInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        Seminar seminar = seminarDao.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        map.put("id",seminar.getID());
        map.put("topic",seminar.getSeminarName());
        map.put("intro",seminar.getIntroduction());
        map.put("order",seminar.getSeminarSerial());
        map.put("teamNumLimit",seminar.getMaxTeam());
        map.put("signUpStartTime",seminar.getEnrollStartTime());
        map.put("signUpEndTime",seminar.getEnrollEndTime());
        return map;
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
        seminarMapper.updateSeminarReportDDLByKlassAndSeminarID(map);
    }

    public Map<String, Object> getSeminarInfoBySeminarAndKlassID(BigInteger seminarID, BigInteger klassID) {
        TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
        TimeZone.setDefault(tz);
        Map<String, Object> map = new HashMap<>(9);
        Seminar seminar = seminarMapper.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        Integer status = seminarMapper.getStatusBySeminarAndKlassID(seminarID,klassID);
        Timestamp ddl = Timestamp.valueOf(seminarMapper.getReportDDLBySeminarAndKlassID(seminarID,klassID));
        map.put("id",seminar.getID());
        map.put("topic",seminar.getSeminarName());
        map.put("intro",seminar.getIntroduction());
        map.put("order",seminar.getSeminarSerial());
        map.put("teamNumLimit",seminar.getMaxTeam());
        map.put("signUpStartTime",seminar.getEnrollStartTime());
        map.put("signUpEndTime",seminar.getEnrollEndTime());
        map.put("status",status);
        map.put("reportDDL",ddl);
        return map;
    }



    //教师开始或结束讨论课
    @PutMapping("/seminar/{seminarID}/class/{classID}/status")
    public void startOrEndSeminar(@PathVariable("seminarID") BigInteger seminarID,
                                  @PathVariable("classID") BigInteger klassID,
                                  @RequestBody Map<String,Integer> statusMap) {
        Integer status = statusMap.get("status");
        seminarDao.updateSeminarStatus(klassID,seminarID,status);
    }

    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID) {
        seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        greetingController=new GreetingController(klassSeminarID);
    }

    public Map<String,Object> getSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID) {
        return seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
    }



    //修改某次讨论课某队伍成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String, Object> modifyTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                      @PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody Map<String, Object> map) {
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,map);
    }



    //修改某次讨论课某队伍展示成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/presentationscore")
    public Map<String, Object> modifyPreScore(@PathVariable("seminarID") BigInteger seminarID,
                                              @PathVariable("teamID") BigInteger teamID,
                                              @RequestBody Map<String, Object> preScore) {
        Double presentationScore = new Double(preScore.get("presentationScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.put("presentationScore",presentationScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }


    //修改某次讨论课某队伍提问成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/questionscore")
    public Map<String, Object> modifyQuestionScore(@PathVariable("seminarID") BigInteger seminarID,
                                                   @PathVariable("teamID") BigInteger teamID,
                                                   @RequestBody Map<String, Object> quesScore){
        Double questionScore = new Double(quesScore.get("questionScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("questionScore",questionScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }



    //修改某次讨论课某队伍报告成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/reportscore")
    public Map<String, Object> modifyReportScore(@PathVariable("seminarID") BigInteger seminarID,
                                                 @PathVariable("teamID") BigInteger teamID,
                                                 @RequestBody Map<String, Object> repScore){
        Double reportScore = new Double(repScore.get("reportScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("reportScore",reportScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String,Object> createNewAttendance(BigInteger seminarID,BigInteger classID,BigInteger teamID,Integer teamOrder) {
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Attendance attendance=teamDao.createAnAttendance(klass_seminarID,teamID,teamOrder);
        teamMapper.insertAttendance(attendance);

        Map<String,Object> map=new HashMap<>();
        map.put("id",attendance.getID());
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        return map;
    }

    @DeleteMapping("/attendance/{attendanceID}")
    public Map<String,String> cancelRegistion(@PathVariable("attendanceID")
                                                          BigInteger attendanceID){
        Map<String,String> map=new HashMap<>();
        if(teamDao.deleteAttendance(attendanceID)==0) {
            map.put("result","failure");
        } else{
            map.put("result","success");}
        return map;
    }

    @GetMapping("/seminar/{seminarID}/team/{teamID}/attendance")
    public Map<String,Object> checkIfAttendanceBySeminarIDAndTeamID(@PathVariable("seminarID") BigInteger seminarID,
                                                                    @PathVariable("teamID") BigInteger teamID){
        BigInteger klassID=klassMapper.getKlassIDByTeamID(teamID);
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
        Attendance attendance=teamDao.getAttendanceByKlass_SeminarIDAndTeamID(klass_seminarID,teamID);
        Map<String,Object> map=new HashMap<>();
        if(attendance==null){ return null; }
        map.put("teamName",attendance.getTeam().getTeamName());
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        map.put("teamOrder",attendance.getTeamOrder());
        map.put("be_present",attendance.getBePresent());
        map.put("ppt_name",attendance.getPptName());
        map.put("ppt_url",attendance.getPptUrl());
        map.put("report_name",attendance.getReportName());
        map.put("report_url",attendance.getReportUrl());
        return map;
    }
    /*
    @GetMapping("/seminar/{seminarID}/class/{classID}/question")
    public List<Map<String,Object>> getQuestionQueue(@PathVariable("seminarID") BigInteger seminarID,
                                                     @PathVariable("classID") BigInteger classID){
        //两个参数都没用
        Queue<Question> questionQueue=greetingController.getQuestionQueue();
        List<Map<String,Object>> questionsInfoList=new ArrayList<>();

        for (Question question:questionQueue) {
            Map<String,Object>map=new HashMap<>(0);
            Student student=studentDao.getStudentByStudentID(question.getStudentID());
            Team team=teamDao.getTeamByTeamID(question.getTeamID());
            map.put("teamNumber",team.getTeamNumber());
            map.put("studentName",student.getName());
            questionsInfoList.add(map);
        }
        return questionsInfoList;
    }
    */


    @PostMapping("/seminar/{seminarID}/class/{classID}/question")
    public void raiseQuestion(@PathVariable("seminarID") BigInteger seminarID,
                              @PathVariable("classID") BigInteger classID,
                              @RequestBody Question question){
        //question里需要有studentID和attendanceID。
        question.setBeSelected(0);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        question.setKlssSeminarID(klassSeminarID);
        BigInteger teamID=teamMapper.getTeamIDByStudentAndKlassID(question.getStudentID(),classID);
        question.setTeamID(teamID);

        greetingController.addQuestion(question);
    }

    @PutMapping("/question/{questionID}")
    public Map<String,Object> selectQuestion(@PathVariable("questionID")BigInteger questionID)
    {
        //不需要questionID
        Map<String,Object> map=new HashMap<>(0);
        Question question=greetingController.getTopQuestion();
        if(question==null){
            map.put("result","There is no question in queue.");
            return map;
        }
        try{
            greetingController.broadcastQuestion(question);
        }catch (Exception e)
        {
            e.printStackTrace();
        } ;

        questionDao.updateQuestionByQuestion(question);
        map.put("result","success");
        return map;
    }

}
