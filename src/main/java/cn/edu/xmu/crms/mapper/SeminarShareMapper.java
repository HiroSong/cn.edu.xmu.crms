package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.ShareSeminarApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SeminarShareMapper
 * @Description 有关数据库中讨论课共享信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
public interface SeminarShareMapper {

    /**
     * 通过seminarShareID删除共享讨论课信息
     *
     * @param seminarShareID 讨论课共享ID
     * @return Integer 删除条数
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    Integer deleteSeminarShareBySeminarShareID(BigInteger seminarShareID);

    /**
     * 向seminarShare表插入seminarShare对象
     *
     * @param seminarShare 共享讨论课对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertSeminarShareBySeminarShare(ShareSeminarApplication seminarShare);
    /**
     * 通过主课程和从课程ID查找共享课程ID
     *
     * @param mainCourseID 主课程ID
     * @param subCourseID 从课程ID
     * @return BigInteger 共享课程ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getTeamShareIDByMainAndSubCourseID(BigInteger mainCourseID, BigInteger subCourseID);
    /**
     * 获得所有申请的ID
     * @return List<BigInteger> 共享讨论课ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    List<BigInteger> listApplicationID();
    /**
     * 通过主课程和从课程ID查找共享讨论课ID
     *
     * @param id 主课程ID
     * @return ShareTeamApplication 申请对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    Map<String, Object> getApplicationByID(BigInteger id);
    /**
     * 修改申请状态
     *
     * @param shareSeminarApplication 共享讨论课
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateStatusBySeminarShareID(ShareSeminarApplication shareSeminarApplication);
    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();
}
