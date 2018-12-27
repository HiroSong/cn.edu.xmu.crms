package cn.edu.xmu.crms.util.websocket;

import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Question;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

import java.math.BigInteger;
import java.util.*;
/**
 * @ClassName GreetingController
 * @Author LaiShaopeng
 * @Date 2018/12/27 2:06
 **/
public class GreetingController {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;

    private BigInteger klassSeminarID;
    private static Map<BigInteger,Queue<Question>> questionQueueList=new HashMap<>(0);
    private static Map<BigInteger,Set<BigInteger>> studentIDRecorderList=new HashMap<>(0);

    public GreetingController(BigInteger klassSeminarID){
        this.klassSeminarID=klassSeminarID;
        Queue<Question> questionQueue=new LinkedList<>();
        Set<BigInteger> studentIDRecorder=new TreeSet<>();
        questionQueueList.put(klassSeminarID,questionQueue);
        studentIDRecorderList.put(klassSeminarID,studentIDRecorder);
    }

    public Question getQuestion()
    {
        if(questionQueueList.get(klassSeminarID).isEmpty()){
            return null;
        }
        Question question=questionQueueList.get(klassSeminarID).peek();
        while(studentIDRecorderList.get(klassSeminarID).contains(question.getStudentID()))
        {
            questionQueueList.get(klassSeminarID).offer(question);
            questionQueueList.get(klassSeminarID).poll();
            questionQueueList.get(klassSeminarID).remove(question.getStudentID());
            question=questionQueueList.get(klassSeminarID).peek();
            try{
                greeting();
            }catch (Exception e)
            {
                e.printStackTrace();
            } ;
        }
        question.setBeSelected(1);
        return question;
    }

    public Queue<Question> getQuestionQueue()
    {
        return questionQueueList.get(klassSeminarID);
    }

    public boolean addQuestion(Question question)
    {
        if(studentIDRecorderList.get(klassSeminarID).contains(question.getStudentID())){
            return false;
        }
        questionQueueList.get(klassSeminarID).offer(question);
        studentIDRecorderList.get(klassSeminarID).add(question.getStudentID());
        try{
            greeting();
        }catch (Exception e)
        {
          e.printStackTrace();
        } ;
        return true;
    }

    @MessageMapping("/websocket/{seminarID}/{klassID}")
    @SendTo("topic/greetings/all/{seminarID}")
    public Map<String,Object> greeting()throws Exception{
        Map<String,Object> map=new HashMap<>(0);
        map.put("questionNumber",questionQueueList.get(klassSeminarID).size());
        return map;
    }

    @SendTo("topic/greetings/all/{seminarID}")
    public Map<String,Object> selectQuestion(Question question)throws Exception{
        Map<String,Object> map=new HashMap<>(0);
        cn.edu.xmu.crms.entity.Student student=studentDao.getStudentByStudentID(question.getStudentID());
        cn.edu.xmu.crms.entity.Team team=teamDao.getTeamByTeamID(question.getTeamID());
        map.put("teamNumber",team.getTeamNumber());
        map.put("studentName",student.getName());
        return map;
    }

    public void resetQueueAndRecorder()
    {
        questionQueueList.get(klassSeminarID).clear();
        studentIDRecorderList.get(klassSeminarID).clear();
    }
}
