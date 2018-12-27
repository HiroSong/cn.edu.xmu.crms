package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.TeamMapper;
import cn.edu.xmu.crms.mapper.TeamValidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TeamValidDao
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/24 17:10
 **/
@Repository
public class TeamValidDao {
    @Autowired
    TeamValidMapper teamValidMapper;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    KlassDao klassDao;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    CourseMapper courseMapper;


    public TeamValidApplication getApplicationByID(BigInteger id) {
        return teamValidMapper.getApplicationByID(id);
    }

    public List<TeamValidApplication> listAllApplication() {
        List<BigInteger> allID = teamValidMapper.listAllApplicationID();
        List<TeamValidApplication> applications = new ArrayList<>();
        for(int i = 0; i < allID.size(); i++) {
            TeamValidApplication application = this.getApplicationByID(allID.get(i));
            applications.add(application);
        }
        return applications;
    }

    public void updateValidApplicationByID(BigInteger teamValidID, Integer status) {
        TeamValidApplication application = new TeamValidApplication();
        application.setStatus(status);
        application.setID(teamValidID);
        TeamValidApplication oldApplication = teamValidMapper.getApplicationByID(teamValidID);
        Team team = new Team();
        team.setID(oldApplication.getTeam().getID());
        team.setStatus(status);
        teamValidMapper.updateStatusByID(application);
        teamMapper.updateTeamStatusByID(team);
    }

    public Boolean checkTeam(Team team) {
        BigInteger courseID = team.getCourse().getID();
        Course course = courseMapper.getCourseByCourseID(courseID);
        return true;
    }

    public BigInteger getApplicationIDByTeamID(BigInteger teamID) {
        return teamValidMapper.getApplicationIDByTeamID(teamID);
    }

    public BigInteger insertApplicationByTeamValid(TeamValidApplication application) {
        teamValidMapper.insertApplicationByTeamValid(application);
        return teamValidMapper.getLastInsertID();
    }
}
