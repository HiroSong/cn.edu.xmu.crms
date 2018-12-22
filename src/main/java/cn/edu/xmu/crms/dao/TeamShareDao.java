package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.ShareTeamApplication;
import cn.edu.xmu.crms.mapper.TeamShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class TeamShareDao {
    @Autowired
    TeamShareMapper teamShareMapper;

    public void deleteTeamShareByTeamShareID(BigInteger teamShareID) {
        teamShareMapper.deleteTeamShareByTeamShareID(teamShareID);
    }

    public BigInteger insertTeamShareByTeamShare(ShareTeamApplication teamShare) {
        teamShareMapper.insertTeamShareByTeamShare(teamShare);
        return teamShareMapper.getTeamShareIDByMainAndSubCourseID(teamShare.getMainCourseID(),
                teamShare.getSubCourseID());
    }
}
