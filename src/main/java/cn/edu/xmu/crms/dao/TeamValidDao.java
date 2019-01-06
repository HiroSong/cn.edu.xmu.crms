package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
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
    @Autowired
    TeamStrategyMapper teamStrategyMapper;
    @Autowired
    StudentMapper studentMapper;


    public TeamValidApplication getApplicationByID(BigInteger id) {
        return teamValidMapper.getApplicationByID(id);
    }

    public List<TeamValidApplication> listAllApplication() {
        List<BigInteger> allID = teamValidMapper.listAllApplicationID();
        List<TeamValidApplication> applications = new ArrayList<>();
        for(int i = 0; i < allID.size(); i++) {
            TeamValidApplication application = this.getApplicationByID(allID.get(i));
            application.setTeam(teamMapper.getTeamByTeamID(application.getTeam().getID()));
            application.setCourse(courseMapper.getCourseByCourseID(application.getTeam().getCourse().getID()));
            application.setKlass(klassDao.getKlassByKlassID(application.getTeam().getKlass().getID()));
            application.setTeacher(teacherDao.getTeacherByCourseID(application.getCourse().getID()));
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
        if(teamStrategyMapper.listStrategyInfoByCourseID(courseID) == null) {
            return true;
        }
        //队伍内成员数量判断
        Integer minMemberNumber = courseMapper.getCourseMinMemberByCourseID(courseID);
        Integer maxMemberNumber = courseMapper.getCourseMaxMemberByCourseID(courseID);
        List<Student> students = studentMapper.listMembersByTeamAndCourseID(team.getID(),courseID);
        if(students.size() > maxMemberNumber || students.size() < minMemberNumber) {
            return false;
        }
        //小组内成员冲突课程判断
        List<ConflictCourseStrategy> conflictCourses = teamStrategyMapper.listConflictCourse(courseID);
        int flag = 0;BigInteger id = conflictCourses.get(0).getID();
        for(int i = 0; i < conflictCourses.size(); i++) {
            if(!id.equals(conflictCourses.get(i).getID())) {
                id = conflictCourses.get(i).getID();
                flag = 0;
            }
            for(int j = 0; j < students.size(); j++) {
                if(studentMapper.getIDByStudentAndCourseID(students.get(j).getID(),
                        conflictCourses.get(i).getCourseID()) != null) {
                    if(flag == 0) {
                        flag = 1;break;
                    } else {
                        return false;
                    }
                }
            }
        }
        //选修课程人数
        String teamAndStrategy = "TeamAndStrategy";
        if(teamStrategyMapper.getOptionalCourseInfo(courseID).equals(teamAndStrategy)) {
            List<CourseMemberLimitStrategy> courseMemberLimits =
                    teamStrategyMapper.listAndCourseMemberLimitInfo(courseID);
            for(int i = 0; i < courseMemberLimits.size();i++) {
                int count = 0;
                for(int j = 0; j < students.size(); j++) {
                    if(studentMapper.getIDByStudentAndCourseID(students.get(j).getID(),
                            courseMemberLimits.get(i).getCourseID()) != null) {
                        count++;
                    }
                }
                if(count < courseMemberLimits.get(i).getMinMember() ||
                        count > courseMemberLimits.get(i).getMaxMember()) {
                    return false;
                }
            }
            return true;
        } else {
            List<CourseMemberLimitStrategy> courseMemberLimits =
                    teamStrategyMapper.listOrCourseMemberLimitInfo(courseID);
            for(int i = 0; i < courseMemberLimits.size();i++) {
                int count = 0;
                for(int j = 0; j < students.size(); j++) {
                    if(studentMapper.getIDByStudentAndCourseID(students.get(j).getID(),
                            courseMemberLimits.get(i).getCourseID()) != null) {
                        count++;
                    }
                }
                if(count < courseMemberLimits.get(i).getMinMember() ||
                        count > courseMemberLimits.get(i).getMaxMember()) {
                    return true;
                }
            }
            return false;
        }
    }

    public BigInteger getApplicationIDByTeamID(BigInteger teamID) {
        return teamValidMapper.getApplicationIDByTeamID(teamID);
    }

    public BigInteger insertApplicationByTeamValid(TeamValidApplication application) {
        teamValidMapper.insertApplicationByTeamValid(application);
        return teamValidMapper.getLastInsertID();
    }
}
