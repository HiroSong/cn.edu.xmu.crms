package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Deadline;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */
@Mapper
@Repository
public interface DeadlineDao {
    /**
     * 用于通过时间号获取时间对象
     *
     * @param id 时间号
     * @return Course  时间对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    Deadline selectCourseDeadlineById(BigInteger id);
    /**
     * 用于通过时间号获取deadline_id
     *
     * @param id 时间号
     * @return BigInteger  deadline_id
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger selectDeadlineIdByCourseId(BigInteger id);
}
