package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.ShareTeamApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;

/**
 * @ClassName TeamShareMapper
 * @Description 有关数据库中组队共享信息的操作
 * @Author Hongqiwu
 **/
@Mapper
@Repository
public interface TeamShareMapper {
    /**
     * 通过teamShareID删除共享组队信息
     *
     * @param teamShareID 组队共享ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void deleteTeamShareByTeamShareID(BigInteger teamShareID);
    /**
     * 向teamShare表插入teamShare对象
     *
     * @param teamShare 共享组队对象
     * @return BigInteger 共享组队ID
     * @author Hongqiwu
     * @date 2018/11/30 19:45
     */
    BigInteger insertTeamShareByTeamShare(ShareTeamApplication teamShare);
}
