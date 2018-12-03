package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Seminar;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import javax.xml.stream.events.Comment;
import java.math.BigInteger;
import java.util.List;

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
<<<<<<< HEAD
     * 用于通过讨论课id获取讨论课信息
     *
     * @param seminarID 讨论课id
     * @return Seminar 讨论课对象
     * @author LaiShaopeng
     * @date 2018/12/2 21:30
     */
    Seminar selectSeminarBySeminarId(BigInteger seminarID);

    /**
     * 用于通过讨论课id获取轮次信息
     *
     * @param seminarID 讨论课id
     * @return BigInteger 轮次id
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    BigInteger selectRoundIdBySeminarId(BigInteger seminarID);

    /**
     * 用于通过讨论课id获取参与展示小组的信息
     *
     * @param seminarID 讨论课id
     * @return List<BigInteger> 小组id列表
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    List<BigInteger> selectTeamIdBySeminarId(BigInteger seminarID);

    /**
     * 用于通过轮次id获取轮次信息
     *
     * @param roundID 讨论课id
     * @return int 轮次序号
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    int selectRoundOrderByRoundId(BigInteger roundID);

    /**
     * 用于通过讨论课id和班级id获取截止日期id
     *
     * @param seminarID 讨论课id
     * @param classID 班级id
     * @return BigInteger 截止日期id
     * @author LaiShaopeng
     * @date 2018/12/3 8:45
     */
    BigInteger selectDeadLineIdBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID);

    /**
     * 用于通过截止日期id获取截止日期的时间
     *
     * @param DeadlineID 讨论课id
     * @return String 轮次序号
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    List<String> selectDeadLineByDeadLineId(BigInteger DeadlineID);

    /**
     * 用于通过讨论课id和班级id获取讨论课信息
     *
     * @param seminarID 讨论课id
     * @param classID 班级id
     * @return Seminar 讨论课对象
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    Seminar selectSeminarBySeminarIdAndClassId(BigInteger seminarID,BigInteger classID);

    /**
     * 用于向presentation表插入记录
     *
     * @param seminarID 讨论课id
     * @param teamID 小组id
     * @param order 展示顺序
     * @return BigInteger 插入的记录id
     * @author LaiShaopeng
     * @date 2018/12/3 8:25
     */
    int insertPresentation(BigInteger seminarID,BigInteger teamID,Integer order);

    /**
     * 用于向presentation表删除记录
     *
     * @param presentationID 展示id
     * @param teamID 小组id
     * @author LaiShaopeng
     * @date 2018/12/3 13:40
     */
    int deletePresentation(BigInteger presentationID,BigInteger teamID);
    /**
     * 用于通过班级号获取讨论课轮次
     * @param classID 课程号
     * @return BigInteger
     * @auhtor Yanxuehuan
     * @data 2018/12/3 17:29
     */
    List<BigInteger> selectRoundByClassID(BigInteger classID);

}
