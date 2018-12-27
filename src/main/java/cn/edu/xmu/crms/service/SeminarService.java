package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
<<<<<<< HEAD
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.websocket.GreetingController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
=======
import cn.edu.xmu.crms.entity.Attendance;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.entity.Round;
import cn.edu.xmu.crms.entity.Seminar;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> 5d5befc44ed4cebd65dba44c35b069ff8e125256

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

<<<<<<< HEAD
    /**
    *未完成
     ***/
=======


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
        return map;
    }



    //未完成
>>>>>>> 5d5befc44ed4cebd65dba44c35b069ff8e125256
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

    public Map<String, Object> insertNewSeminar(Seminar seminar) {
        BigInteger roundID = roundMapper.getRoundIDByCourseIDAndRoundSerial(seminar);
        Round round = new Round();
        round.setID(roundID);
        seminar.setRound(round);
        seminarMapper.insertSeminarBySeminar(seminar);
        BigInteger seminarID = seminarMapper.getLastInsertID();
        Map<String, Object> map = new HashMap<>(1);
        map.put("id",seminarID);
        List<BigInteger> klassesID = klassMapper.listKlassIDByCourseID(seminar.getCourse().getID());
        for(int i = 0; i < klassesID.size(); i++) {
            seminarMapper.insertKlassSeminarBy2ID(klassesID.get(i), seminarID);
        }
        return map;
    }

    @GetMapping("/seminar/{seminarID}/class")//获得一个讨论课下的班级信息
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

    public void updateSeminarInfoBySeminar(Seminar seminar) {
        seminarMapper.updateSeminarBySeminarID(seminar);
    }

    public void deleteSeminarInfoBySeminarID(BigInteger seminarID) {
        seminarDao.deleteSeminarBySeminarID(seminarID);
    }

    @GetMapping("/seminar/{seminarID}")//获取单个讨论课信息
    public Map<String, Object> getSeminarInfoBySeminarID(@PathVariable("seminarID") BigInteger seminarID) {
        Seminar seminar = seminarDao.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        return this.getSeminarInfo(seminar);
    }

    public void updateSeminarReportDDLByKlassAndSeminarID(Map<String, Object> map) {
        seminarMapper.updateSeminarReportDDLByKlassAndSeminarID(map);
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
        Integer status = seminarMapper.getStatusBySeminarAndKlassID(seminarID,klassID);
        Timestamp ddl = Timestamp.valueOf(seminarMapper.getReportDDLBySeminarAndKlassID(seminarID,klassID));
        map.put("status",status);
        map.put("reportDDL",ddl);
        return map;
    }

    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID) {
        seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        greetingController=new GreetingController(klassSeminarID);
    }


    //获得某次讨论课某个队伍的成绩
    @GetMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String,Object> getTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                  @PathVariable("teamID") BigInteger teamID) {
        return seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
    }

    public Map<String, Object> updateSeminarScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                    Map<String, Object> scoreMap) {
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updatePresentationScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> preScore) {
        Double presentationScore = new Double(preScore.get("presentationScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.put("presentationScore",presentationScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updateQuestionScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> quesScore) {
        Double questionScore = new Double(quesScore.get("questionScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("questionScore",questionScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String, Object> updateReportScoreBySeminarAndTeamID(BigInteger seminarID, BigInteger teamID,
                                                                         Map<String, Object> repScore) {
        Double reportScore = new Double(repScore.get("reportScore").toString());
        Map<String, Object> scoreMap = seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
        scoreMap.replace("reportScore",reportScore);
        return seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,scoreMap);
    }

    public Map<String,Object> createNewAttendance(BigInteger seminarID,BigInteger classID,BigInteger teamID,Integer teamOrder) {
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Attendance attendance=teamDao.createAnAttendance(klassSeminarID,teamID,teamOrder);
        teamMapper.insertAttendance(attendance);
        BigInteger attendanceID=teamMapper.getLastInsertID();
        Map<String,Object> map=new HashMap<>(0);
        map.put("id",attendanceID);
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        return map;
    }

    public Map<String,String> cancelRegistion(BigInteger attendanceID){
        Map<String,String> map=new HashMap<>(0);
        if(teamDao.deleteAttendance(attendanceID)==0) { map.put("result","failure"); }
        else { map.put("result", "success"); }
        return map;
    }

    public Map<String,Object> checkIfAttendanceBySeminarIDAndTeamID(BigInteger seminarID,BigInteger teamID){
        BigInteger klassID=klassMapper.getKlassIDByTeamID(teamID);
        System.out.print(klassID);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
        System.out.print(klassSeminarID);
        Attendance attendance=teamDao.getAttendanceByKlass_SeminarIDAndTeamID(klassSeminarID,teamID);
        Map<String,Object> map=new HashMap<>();
        if(attendance==null){ return null; }

        map.put("teamName",attendance.getTeam().getTeamName());
        map.put("teamNumber",attendance.getTeam().getTeamNumber());
        map.put("teamOrder",attendance.getTeamOrder());
        map.put("be_present",attendance.getBePresent());
        map.put("ppt_name",attendance.getPPTName());
        map.put("ppt_url",attendance.getPPTUrl());
        map.put("report_name",attendance.getReportName());
        map.put("report_url",attendance.getReportUrl());
        return map;
    }

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

    @PostMapping("/seminar/{seminarID}/class/{classID}/question")
    public Map<String,Object> raiseQuestion(@PathVariable("seminarID") BigInteger seminarID,
                                            @PathVariable("classID") BigInteger classID,
                                            @RequestBody Question question){
        //question里需要有studentID和attendanceID。
        question.setBeSelected(0);
        question.setKlssSeminarID(seminarMapper.getKlassSeminarIDByKlassAndSeminarID(classID,seminarID));
        question.setTeamID(teamMapper.getTeamIDByStudentAndKlassID(question.getStudentID(),classID));
        Map<String,Object> map=new HashMap<>(0);
        if(greetingController.addQuestion(question)) {
            questionDao.insertQuestionByQuestion(question);
            map.put("result","success");
        }
        map.put("result","failure");
        map.put("reason","You have raised a question already.");
        return map;
    }

    @PutMapping("/question/{questionID}")
    public Map<String,Object> selectQuestion()
    {
        //不需要questionID
        Map<String,Object> map=new HashMap<>(0);
        Question question=greetingController.getQuestion();
        if(question==null){
            map.put("result","There is no question in queue.");
            return map;
        }
        try{
            greetingController.selectQuestion(question);
        }catch (Exception e)
        {
            e.printStackTrace();
        } ;
        questionDao.updateQuestionByQuestion(question);
        map.put("result","success");
        return map;
    }
}
