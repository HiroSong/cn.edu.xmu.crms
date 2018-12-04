package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
/**
 * @author SongLingbing
 * @date 2018/11/29 14:46
 */
@Mapper
@Repository
public interface CourseDao {
    /**
     * 用于通过队伍id获取队伍信息
     *
     * @param number 课程id
     * @return Course 课程对象
     * @author LaiShaopeng
     * @date 2018/12/2 15:21
     */
    Course selectCourseByNumber(BigInteger number);
}
