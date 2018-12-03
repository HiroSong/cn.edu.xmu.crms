package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.EduClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/11/29 12:32
 */
@Mapper
@Repository
public interface EduClassDao {
    /**
     * 用于通过班级获取班级对象
     *
     * @param id 班级号
     * @return EduClass  课程对象
     * @author Hongqiwu
     * @date 2018/12/1 14:01
     */
    EduClass selectEduClassByClassId(BigInteger id);
    /**
     * 用于通过课程号获取班级号
     *
     * @param id 课程
     * @return ArrayList<>  课程号
     * @author Hongqiwu
     * @date 2018/12/1 14:11
     */
    List<BigInteger> selectEduClassIdByCourseId(BigInteger id);
}
