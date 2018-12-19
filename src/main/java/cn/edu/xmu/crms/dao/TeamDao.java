package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Team;
import cn.edu.xmu.crms.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class TeamDao {
    @Autowired
    TeamMapper teamMapper;

    public Team getTeamByTeamID(BigInteger teamID) {
        return teamMapper.getTeamByTeamID(teamID);
    }

    public Team getTeamByCourseAndStudentID(BigInteger courseID, BigInteger studentID) {
        BigInteger teamID = teamMapper.getTeamIDByStudentAndCourseID(studentID, courseID);
        Team team = teamMapper.getTeamByTeamID(teamID);
        return team;
    }

    public List<Team> listTeamsByCourseID(BigInteger courseID) {
        List<Team> teams = new ArrayList<>();
        List<BigInteger> teamsID = teamMapper.listTeamsIDByCourseID(courseID);
        for(int i = 0; i < teamsID.size(); i++) {
            teams.add(teamMapper.getTeamByTeamID(teamsID.get(i)));
        }
        return teams;
    }
}
