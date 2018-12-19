package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 14:44
 */
@Mapper
@Repository
public interface TeacherDao {
    /**
     * 用于通过教师教工号获取教师对象
     *
     * @param number 教工号
     * @return Teacher  教师对象
     * @author SongLingbing
     * @date 2018/11/29 22:40
     */
    Teacher selectTeacherByNumber(BigInteger number);

    /**
     * 用于通过教师id获取教师信息
     * @param id 教师id号
     * @return Teacher 教师对象
     * @author hongqiwu
     * @date 2018/12/01 15:09
     */
    Teacher getTeacherByTeacherID(BigInteger id);
}
