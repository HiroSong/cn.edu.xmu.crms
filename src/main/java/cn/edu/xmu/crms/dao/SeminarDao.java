package cn.edu.xmu.crms.dao;
import cn.edu.xmu.crms.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:30
 */
@Mapper
@Repository
public interface SeminarDao{
    /**
     * 用于通过班级号获取讨论课轮次
     * @param classID 课程号
     * @return BigInteger
     * @auhtor Yanxuehuan
     * @data 2018/12/3 17:29
     */
    List<BigInteger> selectRoundByClassID(BigInteger classID);
}
