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


    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID) {
        seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        greetingController=new GreetingController(klassSeminarID);
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
