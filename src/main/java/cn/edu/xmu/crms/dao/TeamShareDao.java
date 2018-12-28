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
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherMapper teacherMapper;

    public ShareTeamApplication getTeamShareApplicationByID(BigInteger id) {
        Map<String, Object> map = teamShareMapper.getApplicationByID(id);
        ShareTeamApplication application = new ShareTeamApplication();
        application.setID(id);
        Object statusObject = map.get("status");
        if(statusObject == null) {
            application.setStatus(null);
        }
        else {
            application.setStatus(Integer.parseInt(map.get("status").toString()));
        }
        Course mainCourse = courseDao.getCourseByCourseID(new BigInteger(map.get("mainCourseID").toString()));
        application.setMainCourse(mainCourse);
        Course subCourse = courseDao.getCourseByCourseID(new BigInteger(map.get("subCourseID").toString()));
        application.setSubCourse(subCourse);
        Teacher mainTeacher = teacherDao.getTeacherByCourseID(new BigInteger(map.get("mainCourseID").toString()));
        application.setMainCourseTeacher(mainTeacher);
        Teacher subTeacher = teacherDao.getTeacherByCourseID(new BigInteger(map.get("subCourseID").toString()));
        application.setSubCourseTeacher(subTeacher);
        return application;
    }

    public Integer deleteTeamShareByTeamShareID(BigInteger teamShareID) {
        return teamShareMapper.deleteTeamShareByTeamShareID(teamShareID);
    }

    //创建一个新的组队共享
    public BigInteger insertTeamShare(BigInteger mainCourseID,BigInteger subCourseID) {
        ShareTeamApplication application = new ShareTeamApplication();
        application.setMainCourse(new Course());
        application.getMainCourse().setID(mainCourseID);
        application.setSubCourse(new Course());
        application.getSubCourse().setID(subCourseID);
        application.setSubCourseTeacher(new Teacher());
        application.getSubCourseTeacher().setID(teacherMapper.getTeacherIDByCourseID(subCourseID));
        application.setStatus(null);
        teamShareMapper.insertTeamShare(application);
        return teamShareMapper.getLastInsertID();
    }

    public List<ShareTeamApplication> listAllApplications() {
        List<ShareTeamApplication> applications = teamShareMapper.listAllApplications();
        for(int i = 0; i < applications.size(); i++) {
            BigInteger subCourseID = applications.get(i).getSubCourse().getID();
            BigInteger mainCourseID = applications.get(i).getMainCourse().getID();
            applications.get(i).getSubCourse().setCourseName(courseMapper.getCourseNameByCourseID(subCourseID));
            applications.get(i).setMainCourseTeacher(teacherDao.getTeacherByCourseID(mainCourseID));
        }
        return applications;
    }

    //更新共享组队申请的状态
    public void updateStatusByTeamShareID(ShareTeamApplication application) {
        teamShareMapper.updateStatusByTeamShareID(application);
        if(application.getStatus() == 1) {
            Map<String,Object> oldApplication = teamShareMapper.getApplicationByID(application.getID());
            BigInteger mainCourseID = new BigInteger(oldApplication.get("mainCourseID").toString());
            BigInteger subCourseID = new BigInteger(oldApplication.get("subCourseID").toString());
            courseMapper.updateTeamMainCourseID(mainCourseID,subCourseID);
        }
    }
}
