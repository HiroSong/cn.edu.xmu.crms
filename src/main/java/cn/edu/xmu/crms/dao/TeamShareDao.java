package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.ShareTeamApplication;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import cn.edu.xmu.crms.mapper.TeamShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */

@Repository
public class TeamShareDao {
    @Autowired
    TeamShareMapper teamShareMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherDao teacherDao;

    public void deleteTeamShareByTeamShareID(BigInteger teamShareID) {
        teamShareMapper.deleteTeamShareByTeamShareID(teamShareID);
    }

    public ShareTeamApplication insertTeamShareByTeamShare(ShareTeamApplication application) {
        teamShareMapper.insertTeamShareByTeamShare(application);
        BigInteger applicationID = teamShareMapper.getLastInsertID();
        application.setID(applicationID);
        return application;
    }

    public List<ShareTeamApplication> listAllApplications() {
        List<BigInteger> allID = teamShareMapper.listApplicationID();
        List<ShareTeamApplication> allApplications = new ArrayList<>();
        for(int i = 0; i < allID.size(); i++) {
            Map<String,Object> map = teamShareMapper.getApplicationByID(allID.get(i));
            ShareTeamApplication application = new ShareTeamApplication();
            Course mainCourse = courseMapper.getCourseByCourseID(new BigInteger(map.get("mainCourseID").toString()));
            Course subCourse = courseMapper.getCourseByCourseID(new BigInteger(map.get("subCourseID").toString()));
            Teacher mainTeacher = teacherDao.getTeacherByCourseID(new BigInteger(map.get("mainCourseID").toString()));
            Teacher subTeacher = teacherDao.getTeacherByCourseID(new BigInteger(map.get("subCourseID").toString()));
            application.setMainCourse(mainCourse);
            application.setSubCourse(subCourse);
            application.setMainCourseTeacher(mainTeacher);
            application.setSubCourseTeacher(subTeacher);
            Object status = map.get("status");
            if(status == null) {
                application.setStatus(null);
            }
            else {
                application.setStatus(Integer.parseInt(map.get("status").toString()));
            }
            allApplications.add(application);
        }
        return allApplications;
    }
}
