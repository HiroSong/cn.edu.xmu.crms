package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
/**
 * @ClassName QuestionMapper
 * @Description 有关数据库中提问信息的操作
 * @Author LaiShaopeng
 **/
@Mapper
@Repository
public interface QuestionMapper {

    /**
     * 向question表插入question对象
     *
     * @param question 提问对象
     * @return BigInteger 提问ID
     * @author LaiShaopeng
     * @date 2018/12/27 3:17
     */
    BigInteger insertQuestionByQuestion(Question question);
    /**
     * 向question表更新question值
     *
     * @param question 提问对象
     * @author LaiShaopeng
     * @date 2018/12/27 3:20
     */
    public void updateQuestionByQuestion(Question question);
}
