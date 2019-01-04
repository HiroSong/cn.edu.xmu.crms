package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.*;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.email.Email;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import cn.edu.xmu.crms.util.websocket.SeminarRoom;
import com.alibaba.druid.sql.dialect.mysql.ast.MysqlForeignKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    QuestionDao questionDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    SeminarRoom seminarRoom;
    @Autowired
    StudentMapper studentMapper;


    public void updateSeminarStatus(BigInteger klassID, BigInteger seminarID) {
        seminarMapper.updateStartSeminarByKlassAndSeminarID(klassID,seminarID);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDByKlassAndSeminarID(klassID,seminarID);
        seminarRoom=new SeminarRoom(klassSeminarID);
    }

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
        map.put("roundID",seminar.getRound().getID());
        map.put("reportDDL",seminar.getReportDDL());
        return map;
    }

    //获取正在进行的讨论课
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

    //发布新的讨论课
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
        Map<String,Object> map = new HashMap<>(0);
        Seminar seminar = seminarDao.getSeminarBySeminarID(seminarID);
        if(seminar == null) {
            return null;
        }
        return this.getSeminarInfo(seminar);
    }


    //教师获取讨论课报告截止时间
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



    //教师开始或结束讨论课
    @PutMapping("/seminar/{seminarID}/class/{classID}/status")
    public void startOrEndSeminar(@PathVariable("seminarID") BigInteger seminarID,
                                  @PathVariable("classID") BigInteger klassID,
                                  @RequestBody Map<String,Integer> statusMap) {
        Integer status = statusMap.get("status");
        seminarDao.updateSeminarStatus(klassID,seminarID,status);
    }


    //获得某次讨论课某个队伍的成绩
    @GetMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String,Object> getTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                  @PathVariable("teamID") BigInteger teamID) {
        return seminarDao.getSeminarScoreBySeminarAndTeamID(seminarID,teamID);
    }



    //修改某次讨论课某队伍成绩
    @PutMapping("/seminar/{seminarID}/team/{teamID}/seminarscore")
    public Map<String, Object> modifyTeamSeminarScore(@PathVariable("seminarID") BigInteger seminarID,
                                                      @PathVariable("teamID") BigInteger teamID,
                                                      @RequestBody Map<String, Object> map) {
        Map<String,Object> resultmap=seminarDao.updateSeminarScoreBySeminarAndTeamID(seminarID,teamID,map);
        List<String> emailList=studentMapper.listMemberEmailsByTeamID(teamID);
        for (String emailCount:emailList) {
            if(emailCount!=null){
                String emailContent="您的讨论课程成绩已更新。";
                Email email=new Email();
                email.sendSimpleMail(emailCount,emailContent);
            }
        }
        return resultmap;
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

    @PostMapping("/seminar/{seminarID}/class/{classID}/attendance")
    public Map<String,Object> createNewAttendance(@PathVariable("seminarID")BigInteger seminarID,
                                                  @PathVariable("classID")BigInteger classID,
                                                  @RequestBody Map<String,Object> teamIDAndOrder) {
        BigInteger teamID=new BigInteger(teamIDAndOrder.get("teamID").toString());
        Integer teamOrder=Integer.parseInt(teamIDAndOrder.get("teamOrder").toString());
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Attendance attendance=teamDao.createAnAttendance(klass_seminarID,teamID,teamOrder);
        teamMapper.insertAttendance(attendance);
        BigInteger attendanceID=teamMapper.getLastInsertID();
        Map<String,Object> map=new HashMap<>();
        map.put("id",attendanceID);
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
        BigInteger klassID=klassMapper.getKlassIDBySeminarAndTeamID(seminarID,teamID);
        BigInteger klass_seminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,klassID);
        Attendance attendance=teamDao.getAttendanceByKlassSeminarIDAndTeamID(klass_seminarID,teamID);
        Map<String,Object> map=new HashMap<>();
        if(attendance==null){ return null; }
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

    @PostMapping("/seminar/{seminarID}/class/{classID}/question")
    public void raiseQuestion(@PathVariable("seminarID") BigInteger seminarID,
                              @PathVariable("classID") BigInteger classID,
                              @RequestBody Question question, HttpServletRequest request){
        //question里需要有studentID和attendanceID。
        question.setBeSelected(0);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        question.setKlssSeminarID(klassSeminarID);
        BigInteger teamID=teamMapper.getTeamIDByStudentAndKlassID(question.getStudentID(),classID);
        question.setTeamID(teamID);

        seminarRoom.addQuestion(question);
    }

    @PutMapping("/question")
    public Map<String,Object> selectQuestion()
    {
        //不需要questionID
        Map<String,Object> map=new HashMap<>(0);
        Question question=seminarRoom.getTopQuestion();
        if(question==null){
            map.put("result","There is no question in queue.");
            return map;
        }
        try{
            seminarRoom.broadcastQuestion(question);
        }catch (Exception e)
        {
            e.printStackTrace();
            map.put("result","Fail to broadcastQuestion.");
            return map;
        } ;

        map.put("result","success");
        return map;
    }

    /**
     * @Author LaiShaopeng
     * @param seminarID
     * @param classID
     * 切换分组
     */
    @PutMapping("/seminar/{seminarID}/class/{classID}/process/attendance")
    public void switchAttendance(@PathVariable("seminarID")BigInteger seminarID,
                                 @PathVariable("classID")BigInteger classID)
    {
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        seminarRoom.resetQueue(klassSeminarID);
    }
    /**
     * @Author LaiShaopeng
     * @param seminarID
     * @param classID
     * @param order
     * @param score
     * 为某个提问打分
     */
    @PutMapping("/seminar/{seminarID}/class/{classID}/question/{order}/{score}")
    public void updateQuestionScore(@PathVariable("seminarID") BigInteger seminarID,
                                    @PathVariable("classID") BigInteger classID,
                                    @PathVariable("order") Integer order,
                                    @PathVariable("score") String score)
    {
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Double questionScore= Double.parseDouble(score);
        seminarRoom.updateQuestionScore(klassSeminarID,order,questionScore);
    }
}
