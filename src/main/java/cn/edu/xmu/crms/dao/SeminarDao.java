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
     * @param classID 班级号
     * @return BigInteger
     * @auhtor Yanxuehuan
     * @data 2018/12/3 17:29
     */
    List<BigInteger> selectRoundByClassID(BigInteger classID);
    /**
     * 用于通过班级号和讨论课号获取讨论课详情
     * @param classID,seminarID 班级号，讨论课号
     * @return Seminar
     * @auhtor Yanxuehuan
     * @data 2018/12/3 20:57
     */
    Seminar selectSeminarBySeminarIDandClassID(BigInteger seminarID,BigInteger classID);
}
