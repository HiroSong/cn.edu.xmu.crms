package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.ShareSeminarApplication;
import cn.edu.xmu.crms.mapper.SeminarShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class SeminarShareDao {
    @Autowired
    SeminarShareMapper seminarShareMapper;

    public void deleteSeminarShareBySeminarShareID(BigInteger seminarShareID) {
        seminarShareMapper.deleteSeminarShareBySeminarShareID(seminarShareID);
    }

    public BigInteger insertSeminarShareByTeamShare(ShareSeminarApplication seminarShare) {
        return seminarShareMapper.insertSeminarShareBySeminarShare(seminarShare);
    }
}
