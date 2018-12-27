package cn.edu.xmu.crms.mapper;

import cn.edu.xmu.crms.entity.TeamValidApplication;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamValidMapper
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/24 16:58
 **/
@Mapper
@Component
public interface TeamValidMapper {
    /**
     * 获得所有申请的ID
     * @return List<BigInteger> 额外组队申请ID列表
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    List<BigInteger> listAllApplicationID();
    /**
     * 通过主课程和从课程ID查找共享课程ID
     *
     * @param id 共享组队ID
     * @return ShareTeamApplication 申请对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    TeamValidApplication getApplicationByID(BigInteger id);
    /**
     * 更新额外申请的状态
     *
     * @param teamValidApplication 额外组队申请对象
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    void updateStatusByID(TeamValidApplication teamValidApplication);

    /**
     * 获得申请ID
     *
     * @param teamID 队伍ID
     * @return BigInteger 申请ID
     * @author Hongqiwu
     * @date 2018/12/18 19:35
     */
    BigInteger getApplicationIDByTeamID(BigInteger teamID);
}
