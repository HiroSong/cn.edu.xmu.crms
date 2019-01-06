package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.ShareTeamApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;


/**
 * @ClassName TeamShareMapper
 * @Description 有关数据库中组队共享信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Component
public interface TeamShareMapper {

    /**
     * 通过teamShareID删除共享组队信息
     *
     * @param teamShareID 组队共享ID
     * @return Integer 删除条数
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    Integer deleteTeamShareByTeamShareID(BigInteger teamShareID);

    /**
     * 向teamShare表插入teamShare对象
     *
     * @param teamShare 共享组队对象
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    void insertTeamShare(ShareTeamApplication teamShare);
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
     * 获得所有申请
     * @return 获得所有申请
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    List<ShareTeamApplication> listAllApplications();
    /**
     * 通过主课程和从课程ID查找共享课程ID
     *
     * @param id 共享组队ID
     * @return ShareTeamApplication 申请对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    Map<String, Object> getApplicationByID(BigInteger id);
    /**
     * 修改申请状态
     *
     * @param shareTeamApplication 共享组队
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateStatusByTeamShareID(ShareTeamApplication shareTeamApplication);
    /**
     * 获得上一次插入语句的ID
     *
     * @return BigInteger 上一条插入语句的ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getLastInsertID();
}
