package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.TeamValidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public TeamValidApplication getApplicationByID(BigInteger id) {
        Map<String, Object> map = teamValidMapper.getApplicationByID(id);
        TeamValidApplication application = new TeamValidApplication();
        BigInteger teamID = new BigInteger(map.get("teamID").toString());
        BigInteger teacherID = new BigInteger(map.get("teacherID").toString());
        application.setID(id);
        application.setReason(map.get("reason").toString());
        Object status = map.get("status");
        if(status == null) {
            application.setStatus(null);
        }
        else {
            application.setStatus(Integer.parseInt(map.get("status").toString()));
        }
        Team team = teamDao.getTeamByTeamID(teamID);
        Student leader = team.getLeader();
        Teacher teacher = teacherDao.getTeacherByTeacherID(teacherID);
        Klass klass = team.getKlass();
        Course course = team.getCourse();
        application.setTeam(team);
        application.setLeader(leader);
        application.setTeacher(teacher);
        application.setKlass(klass);
        application.setCourse(course);
        return application;
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
}
