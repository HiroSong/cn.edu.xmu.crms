package cn.edu.xmu.crms.util.websocket;


import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.QuestionMapper;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Question;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.*;
/**
 * @ClassName SeminarRoom
 * @Author LaiShaopeng
 * @Date 2018/12/27 2:06
 **/
@Controller
public class SeminarRoom {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    TeamMapper teamMapper;

    private Integer count;
    private static Map<BigInteger,Queue<Question>> questionQueueList=new HashMap<>(0);
    private static Map<BigInteger,List<Question>> questionSelectedQueueList=new HashMap<>(0);

    public SeminarRoom(){
        count=0;
    }

    public SeminarRoom(BigInteger klassSeminarID){
        Queue<Question> questionQueue=new LinkedList<>();
        List<Question> questionSelectedQueue=new ArrayList<>();

        count=0;
        questionQueueList.put(klassSeminarID,questionQueue);
        questionSelectedQueueList.put(klassSeminarID,questionSelectedQueue);
    }

    public Question getTopQuestion(BigInteger klassSeminarID)
    {
        if(questionQueueList.get(klassSeminarID).isEmpty()){
            return null;
        }
        Question question=questionQueueList.get(klassSeminarID).poll();
        question.setBeSelected(1);
        questionSelectedQueueList.get(klassSeminarID).add(question);
        return question;
    }

    //给某个问题打分
    public boolean updateQuestionScore(BigInteger klassSeminarID,Integer order,Double score){
        for(int i=0;i<questionSelectedQueueList.get(klassSeminarID).size();i++)
        {
            if(questionSelectedQueueList.get(klassSeminarID).get(i).order.equals(order)){
                questionSelectedQueueList.get(klassSeminarID).get(i).setScore(score);
                questionMapper.insertQuestionByQuestion(questionSelectedQueueList.get(klassSeminarID).get(i));
                return true;
            }
        }
        return false;
    }

    public boolean addQuestion(Question question)
    {
        count=count+1;
        question.order=count;
        questionQueueList.get(question.getKlssSeminarID()).offer(question);
        return true;
    }

    /**
     * 获得提问队列和已被抽取的提问的队列的信息。
     * @Author LaiShaopeng
     * @return map 装有提问队列和已被抽取的提问的队列的提问信息。
     */
    public Map<String,Object> greeting(BigInteger klassSeminarID){
        Map<String,Object> map=new HashMap<>(0);
        List<Map<String,Object>> questionQueue=new ArrayList<>();
        List<Map<String,Object>> questionSelectedQueue=new ArrayList<>();

        for (Question question:questionQueueList.get(klassSeminarID)) {
            Map<String,Object>questionInfo=new HashMap<>(0);
            Student student=studentDao.getStudentByStudentID(question.getStudentID());
            Team team=teamDao.getTeamByTeamID(question.getTeamID());
            questionInfo.put("teamNumber",team.getTeamNumber());
            questionInfo.put("studentName",student.getName());
            questionInfo.put("order",question.order);
            questionQueue.add(questionInfo);
            System.out.println(question.order);
        }
        map.put("questionQueue",questionQueue);

        for (Question question:questionSelectedQueueList.get(klassSeminarID)) {
            Map<String,Object>questionInfo=new HashMap<>(0);
            Student student=studentDao.getStudentByStudentID(question.getStudentID());
            Team team=teamDao.getTeamByTeamID(question.getTeamID());
            questionInfo.put("teamNumber",team.getTeamNumber());
            questionInfo.put("studentName",student.getName());
            questionInfo.put("order",question.order);
            questionSelectedQueue.add(questionInfo);
        }
        map.put("questionSelectedQueue",questionSelectedQueue);

        return map;
    }

    public void resetQueue(BigInteger klassSeminarID)
    {
        count=0;
        questionQueueList.get(klassSeminarID).clear();
        questionSelectedQueueList.get(klassSeminarID).clear();;
    }

    /**
     * @author LaiShaopeng
     * @param question
     * @return map 抽取到的提问的发起该提问的学生的组号和姓名。
     */
    public Map<String,Object> broadcastQuestion(Question question){
        Map<String,Object> map=new HashMap<>(0);
        cn.edu.xmu.crms.entity.Student student=studentDao.getStudentByStudentID(question.getStudentID());
        cn.edu.xmu.crms.entity.Team team=teamDao.getTeamByTeamID(question.getTeamID());
        map.put("teamNumber",team.getTeamNumber());
        map.put("studentName",student.getName());
        return map;
    }

    /**
     * 学生发起提问
     *
     * @param seminarID
     * @param classID
     * @param question
     * @return Map 两个问题队列
     * @author Laishaopeng
     * @date 2019/1/4 20:41
     */
    @MessageMapping("/seminar/{seminarID}/class/{classID}/question")
    @SendTo("/topic/greetings/all/{seminarID}/class/{classID}/question")
    public Map<String,Object> raiseQuestion(@DestinationVariable ("seminarID") BigInteger seminarID,
                              @DestinationVariable("classID") BigInteger classID,
                              @RequestBody Question question){
        //question里需要有studentID和attendanceID。
        question.setBeSelected(0);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        question.setKlssSeminarID(klassSeminarID);
        BigInteger teamID=teamMapper.getTeamIDByStudentAndKlassID(question.getStudentID(),classID);
        question.setTeamID(teamID);

        addQuestion(question);
        return greeting(klassSeminarID);
    }

    /**
     * 老师抽取提问
     * @param seminarID
     * @param classID
     * @return Map 两个提问队列
     * @author Laishaopeng
     * @date 2019/1/4 20:51
     */
    @MessageMapping("/seminar/{seminarID}/class/{classID}/selectquestion")
    @SendTo("/topic/greetings/all/{seminarID}/class/{classID}/selectquestion")
    public Map<String,Object> selectQuestion(@DestinationVariable("seminarID") BigInteger seminarID,
                                             @DestinationVariable("classID") BigInteger classID)
    {
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Map<String,Object> map=new HashMap<>(0);
        Question question=getTopQuestion(klassSeminarID);
        if(question==null){
            map.put("result","There is no question in queue.");
            return map;
        }
        return broadcastQuestion(question);
    }

    /**
     * 切换分组
     *
     * @param seminarID
     * @param classID
     * @author Laishaopeng
     * @date 2019/1/4 20:51
     **/
    @MessageMapping("/seminar/{seminarID}/class/{classID}/process/attendance")
    @SendTo("/topic/greetings/all/{seminarID}/class/{classID}/attendance")
    public Map<String,Object> switchAttendance(@DestinationVariable("seminarID")BigInteger seminarID,
                                 @DestinationVariable("classID")BigInteger classID,
                                 @RequestBody Map<String,Object> oldAndNewAttendanceID)
    {
        BigInteger oldAttendanceID=new BigInteger(oldAndNewAttendanceID.get("oldAttendanceID").toString());
        BigInteger newAttendanceID=new BigInteger(oldAndNewAttendanceID.get("newAttendanceID").toString());
        teamDao.updateAttendanceStatus(newAttendanceID,1);
        teamDao.updateAttendanceStatus(oldAttendanceID,2);
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        resetQueue(klassSeminarID);
        return greeting(klassSeminarID);
    }

    /**
     * @param seminarID
     * @param classID
     * @param order
     * @param score
     * @author LaiShaopeng
     * @date 2019/1/4 20:52
     * 为某个提问打分
     */
    @MessageMapping("/seminar/{seminarID}/class/{classID}/question/{order}/{score}")
    @SendTo("/topic/greetings/all/{seminarID}/class/{classID}/score")
    public Map<String,Object> updateQuestionScore(@DestinationVariable("seminarID") BigInteger seminarID,
                                    @DestinationVariable("classID") BigInteger classID,
                                    @DestinationVariable("order") Integer order,
                                    @DestinationVariable("score") String score)
    {
        BigInteger klassSeminarID=seminarMapper.getKlassSeminarIDBySeminarIDAndClassID(seminarID,classID);
        Double questionScore= Double.parseDouble(score);
        updateQuestionScore(klassSeminarID,order,questionScore);

        return greeting(klassSeminarID);
    }
}

