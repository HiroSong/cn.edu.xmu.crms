package cn.edu.xmu.crms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:30
 */
@Mapper
@Repository
public interface SeminarDao{
    /**
     * 查找并返回正在展示的小组id
     *
     * @return BigInteger  小组号
     * @author Hongqiwu
     * @date 2018/12/3 15:46
     */
     public BigInteger selectShowingTeamId();
}
