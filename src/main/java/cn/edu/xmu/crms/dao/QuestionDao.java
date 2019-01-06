package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Question;
import cn.edu.xmu.crms.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

/**
 * @author LaiShaopeng
 * @date 2018/12/27 3:01
 */
@Repository
public class QuestionDao {

    @Autowired
    QuestionMapper questionMapper;

    public BigInteger insertQuestionByQuestion(Question question){
        questionMapper.insertQuestionByQuestion(question);
        return question.getID();
    }

    public void updateQuestionByQuestion(Question question){questionMapper.updateQuestionByQuestion(question);}
}
