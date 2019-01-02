package cn.edu.xmu.crms.util.websocket;


import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Question;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;
/**
 * @ClassName SeminarRoom
 * @Author LaiShaopeng
 * @Date 2018/12/27 2:06
 **/
public class SeminarRoom {
    @Autowired
    TeamDao teamDao;
    @Autowired
    StudentDao studentDao;

    private BigInteger klassSeminarID;
    private Integer count;
    private static Map<BigInteger,Queue<Question>> questionQueueList=new HashMap<>(0);
    private static Map<BigInteger,List<Question>> questionSelectedQueueList=new HashMap<>(0);

    public SeminarRoom(BigInteger klassSeminarID){
        this.klassSeminarID=klassSeminarID;
        Queue<Question> questionQueue=new LinkedList<>();
        List<Question> questionSelectedQueue=new ArrayList<>();

        count=0;
        questionQueueList.put(klassSeminarID,questionQueue);
        questionSelectedQueueList.put(klassSeminarID,questionSelectedQueue);
    }

    public Question getTopQuestion()
    {
        if(questionQueueList.get(klassSeminarID).isEmpty()){
            return null;
        }
        Question question=questionQueueList.get(klassSeminarID).poll();
        question.setBeSelected(1);
        questionSelectedQueueList.get(klassSeminarID).add(question);
        return question;
    }

    public boolean updateQuestionScore(Question question,Double score){
        for(int i=0;i<questionSelectedQueueList.get(klassSeminarID).size();i++)
        {
            if(questionSelectedQueueList.get(klassSeminarID).get(i).equals(question)){
                questionSelectedQueueList.get(klassSeminarID).get(i).setScore(score);
                return true;
            }
        }
        return false;
    }

    public boolean addQuestion(Question question)
    {
        count=count+1;
        question.order=count;
        questionQueueList.get(klassSeminarID).offer(question);

        try{
            greeting();
        }catch (Exception e)
        {
            e.printStackTrace();
        } ;
        return true;
    }

    /**
     * 获得提问队列和已被抽取的提问的队列的信息。
     * @Author LaiShaopeng
     * @return map 装有提问队列和已被抽取的提问的队列的提问信息。
     * @throws Exception
     */
    @SendTo("topic/greetings/all/{seminarID}")
    public Map<String,Object> greeting()throws Exception{
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

    /**
     * @Author LaiShaopeng
     * @param question
     * @return map 抽取到的提问的发起该提问的学生的组号和姓名。
     * @throws Exception
     */
    @SendTo("topic/greetings/all/{seminarID}")
    public Map<String,Object> broadcastQuestion(Question question)throws Exception{
        Map<String,Object> map=new HashMap<>(0);
        cn.edu.xmu.crms.entity.Student student=studentDao.getStudentByStudentID(question.getStudentID());
        cn.edu.xmu.crms.entity.Team team=teamDao.getTeamByTeamID(question.getTeamID());
        map.put("teamNumber",team.getTeamNumber());
        map.put("studentName",student.getName());
        return map;
    }

    public void resetQueue()
    {
        count=0;
        questionQueueList.get(klassSeminarID).clear();
        questionSelectedQueueList.get(klassSeminarID).clear();;
    }
}

