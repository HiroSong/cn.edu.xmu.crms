package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.TeamStrategy;
import cn.edu.xmu.crms.mapper.TeamStrategyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * @ClassName TeamStrategyDao
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/28 10:42
 **/
@Repository
public class TeamStrategyDao {
    @Autowired
    TeamStrategyMapper teamStrategyMapper;

}
