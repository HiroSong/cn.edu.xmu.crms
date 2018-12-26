package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.SeminarShareDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.ShareSeminarApplication;
import cn.edu.xmu.crms.entity.ShareTeamApplication;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import cn.edu.xmu.crms.mapper.SeminarShareMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName CourseService
 * @Author Hongqiw
 **/
@RestController
@Service
public class SeminarShareService {
    @Autowired
    SeminarShareDao seminarShareDao;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    SeminarShareMapper seminarShareMapper;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Map<String,Object> getApplicationInfo(ShareSeminarApplication application,BigInteger teacherID) {
        Map<String,Object> map = new HashMap<>(8);
        map.put("id",application.getID());
        map.put("mainCourseID",application.getMainCourse().getID());
        map.put("mainCourseName",application.getMainCourse().getCourseName());
        map.put("subCourseID",application.getSubCourse().getID());
        map.put("subCourseName",application.getSubCourse().getCourseName());
        map.put("subCourseTeacherID",application.getSubCourseTeacher().getID());
        map.put("subCourseTeacherName",application.getSubCourseTeacher().getName());
        map.put("mainCourseTeacherID",application.getMainCourseTeacher().getID());
        map.put("mainCourseTeacherName",application.getMainCourseTeacher().getName());
        map.put("isMainCourse",teacherID.equals(application.getMainCourseTeacher().getID()));
        return map;
    }

    public void deleteSeminarShareBySeminarShareID(BigInteger seminarShareID) {
        seminarShareDao.deleteSeminarShareBySeminarShareID(seminarShareID);
    }
    /**
     * 用于courseID查找共享讨论课的主课程和从课程信息
     *
     * @param courseID 课程号码
     * @return List<Map<String, Object>> 返回查找到的信息，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    @GetMapping("/course/{courseID}/seminarshare")
    public List<Map<String, Object>> listAllSeminarShareByCourseID(@PathVariable("courseID") BigInteger courseID) {
        List<Map<String, Object>> courseMapList = new ArrayList<>();
        List<Course> mainCourseList = seminarDao.listMainCoursesByCourseID(courseID);
        List<Course> subCourseList = seminarDao.listSubCoursesByCourseID(courseID);
        Course course = courseDao.getCourseByCourseID(courseID);
        if(mainCourseList == null && subCourseList == null) {
            return null;
        }
        for(int i = 0; i < mainCourseList.size(); i++) {
            Course mainCourse = mainCourseList.get(i);
            Map<String, Object> mainCourseMap = new HashMap<>(3);
            Map<String, Object> masterMap = new HashMap<>(3);
            Map<String, Object> receiveMap = new HashMap<>(3);
            masterMap.put("masterCourseID",mainCourse.getID());
            masterMap.put("masterCourseName",mainCourse.getCourseName());
            masterMap.put("teacherName",mainCourse.getTeacher().getName());
            receiveMap.put("receiveCourseID",courseID);
            receiveMap.put("receiveCourseName",course.getCourseName());
            receiveMap.put("teacherName",course.getTeacher().getName());
            BigInteger shareID = seminarMapper.getSeminarShareIDByMainAndSubCourseID(mainCourse.getID(), courseID);
            mainCourseMap.put("seminarShareID",shareID);
            mainCourseMap.put("masterCourse",masterMap);
            mainCourseMap.put("receiveCourse",receiveMap);
            mainCourseMap.put("isMainCourse",false);
            courseMapList.add(mainCourseMap);
        }
        for(int i = 0; i < subCourseList.size(); i++) {
            Course subCourse = subCourseList.get(i);
            Map<String, Object> subCourseMap = new HashMap<>(3);
            Map<String, Object> masterMap = new HashMap<>(3);
            Map<String, Object> receiveMap = new HashMap<>(3);
            masterMap.put("masterCourseID",courseID);
            masterMap.put("masterCourseName",course.getCourseName());
            masterMap.put("teacherName",course.getTeacher().getName());
            receiveMap.put("receiveCourseID",subCourse.getID());
            receiveMap.put("receiveCourseName",subCourse.getCourseName());
            receiveMap.put("teacherName",subCourse.getTeacher().getName());
            BigInteger shareID = seminarMapper.getSeminarShareIDByMainAndSubCourseID(courseID, subCourse.getID());
            subCourseMap.put("teamShareID",shareID);
            subCourseMap.put("masterCourse",masterMap);
            subCourseMap.put("receiveCourse",receiveMap);
            subCourseMap.put("isMainCourse",true);
            courseMapList.add(subCourseMap);
        }
        return courseMapList;
    }

    public Map<String, Object> createSeminarShareRequestByCourseID(BigInteger mainCourseID, BigInteger subCourseID) {
        ShareSeminarApplication application = new ShareSeminarApplication();
        application.setMainCourse(new Course());
        application.getMainCourse().setID(mainCourseID);
        application.setSubCourse(new Course());
        application.getSubCourse().setID(subCourseID);
        application.setSubCourseTeacher(new Teacher());
        application.getSubCourseTeacher().setID(teacherMapper.getTeacherIDByCourseID(subCourseID));
        application.setStatus(null);
        ShareSeminarApplication newApplication = seminarShareDao.insertSeminarShareBySeminarShare(application);
        Map<String, Object> map = new HashMap<>(1);
        map.put("seminarShareRequestID",application.getID());
        return map;
    }

    @GetMapping("/request/seminarshare")
    public List<Map<String, Object>> listAllSeminarShareRequest(HttpServletRequest request) {
        BigInteger id = jwtTokenUtil.getIDFromRequest(request);
        List<Map<String, Object>> seminarShareRequest = new ArrayList<>();
        List<ShareSeminarApplication> allApplications = seminarShareDao.listAllApplications();
        for(int i = 0 ; i < allApplications.size(); i++) {
            seminarShareRequest.add(this.getApplicationInfo(allApplications.get(i),id));
        }
        return seminarShareRequest;
    }

    public Map<String, Object> updateSeminarShareStatusByID(BigInteger seminarShareID, Integer status) {
        ShareSeminarApplication application = new ShareSeminarApplication();
        application.setStatus(status);
        application.setID(seminarShareID);
        seminarShareMapper.updateStatusBySeminarShareID(application);
        Map<String,Object> map = new HashMap<>(1);
        if(status == 1) {
            map.put("handledType","accept");
        }
        else if(status == 0) {
            map.put("handledType","reject");
        }
        return map;
    }
}
