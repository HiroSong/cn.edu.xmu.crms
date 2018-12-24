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

    public void deleteSeminarShareBySeminarShareID(BigInteger seminarShareID) {
        seminarShareMapper.deleteSeminarShareBySeminarShareID(seminarShareID);
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
            Map<String,Object> map = seminarShareMapper.getApplicationByID(allID.get(i));
            ShareSeminarApplication application = new ShareSeminarApplication();
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
