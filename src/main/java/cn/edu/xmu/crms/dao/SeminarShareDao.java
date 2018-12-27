package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.ShareSeminarApplication;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.SeminarShareMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
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
public class SeminarShareDao {
    @Autowired
    SeminarShareMapper seminarShareMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    CourseDao courseDao;

    public ShareSeminarApplication getSeminarShareApplicationByID(BigInteger id) {
        Map<String, Object> map = seminarShareMapper.getApplicationByID(id);
        ShareSeminarApplication application = new ShareSeminarApplication();
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

    public Integer deleteSeminarShareBySeminarShareID(BigInteger seminarShareID) {
        return seminarShareMapper.deleteSeminarShareBySeminarShareID(seminarShareID);
    }

    public ShareSeminarApplication insertSeminarShareBySeminarShare(ShareSeminarApplication application) {
        seminarShareMapper.insertSeminarShareBySeminarShare(application);
        BigInteger applicationID = seminarShareMapper.getLastInsertID();
        application.setID(applicationID);
        return application;
    }

    public List<ShareSeminarApplication> listAllApplications() {
        List<BigInteger> allID = seminarShareMapper.listApplicationID();
        List<ShareSeminarApplication> allApplications = new ArrayList<>();
        for(int i = 0; i < allID.size(); i++) {
            ShareSeminarApplication application = this.getSeminarShareApplicationByID(allID.get(i));
            allApplications.add(application);
        }
        return allApplications;
    }
}
