package cn.edu.xmu.crms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:30
 */
@Mapper
@Repository
public interface SeminarDao{
    /**
     * 用于通过课程号获取讨论课轮次
     * @param CourseID 课程号
     * @return round
     * @auhtor Yanxuehuan
     * @data 2018/12/3 10:29
     */
    Round selectRoundByCourseID(BigInteger CourseID);
}
