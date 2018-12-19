package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.ShareSeminarApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

/**
 * @ClassName SeminarShareMapper
 * @Description 有关数据库中讨论课共享信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface SeminarShareMapper {
    /**
     * 通过seminarShareID删除共享讨论课信息
     *
     * @param seminarShareID 讨论课共享ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteSeminarShareBySeminarShareID(BigInteger seminarShareID);
    /**
     * 向seminarShare表插入seminarShare对象
     *
     * @param seminarShare 共享讨论课对象
     * @return BigInteger 共享讨论课ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger insertSeminarShareBySeminarShare(ShareSeminarApplication seminarShare);
}
