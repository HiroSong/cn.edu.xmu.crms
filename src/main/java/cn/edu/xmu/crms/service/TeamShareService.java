package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.dao.TeamShareDao;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.ShareTeamApplication;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName CourseService
 * @Author Hongqiw
 **/
@Service
public class TeamShareService {
    @Autowired
    TeamShareDao teamShareDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;

   public void deleteTeamShareByTeamShareID(BigInteger teamShareID) {
       teamShareDao.deleteTeamShareByTeamShareID(teamShareID);
   }

    /**
     * 用于courseID查找共享组队的主课程和从课程信息
     *
     * @param courseID 课程号码
     * @return List<Map<String, Object>> 返回查找到的信息，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public List<Map<String, Object>> listMainAndSubCoursesInfoByCourseID(BigInteger courseID) {
        List<Map<String, Object>> courseMapList = new ArrayList<>();
        List<Course> mainCourseList = courseDao.listMainCoursesByCourseID(courseID);
        List<Course> subCourseList = courseDao.listSubCoursesByCourseID(courseID);
        Course course = courseDao.getCourseByCourseID(courseID);
        Teacher teacher = teacherDao.getTeacherByTeacherID(course.getTeacherID());
        if(mainCourseList == null && subCourseList == null) {
            return null;
        }
        for(int i = 0; i < mainCourseList.size(); i++) {
            Course mainCourse = mainCourseList.get(i);
            Teacher mainTeacher = teacherDao.getTeacherByTeacherID(mainCourse.getTeacherID());
            Map<String, Object> mainCourseMap = new HashMap<>(3);
            Map<String, Object> masterMap = new HashMap<>(3);
            Map<String, Object> receiveMap = new HashMap<>(3);
            masterMap.put("masterCourseID",mainCourse.getID());
            masterMap.put("masterCourseName",mainCourse.getCourseName());
            masterMap.put("teacherName",mainTeacher.getName());
            receiveMap.put("receiveCourseID",courseID);
            receiveMap.put("receiveCourseName",course.getCourseName());
            receiveMap.put("teacherName",teacher.getName());
            BigInteger shareID = courseMapper.getTeamShareIDByMainAndSubCourseID(mainCourse.getID(), courseID);
            mainCourseMap.put("teamShareID",shareID);
            mainCourseMap.put("masterCourse",masterMap);
            mainCourseMap.put("receiveCourse",receiveMap);
            courseMapList.add(mainCourseMap);
        }
        for(int i = 0; i < subCourseList.size(); i++) {
            Course subCourse = subCourseList.get(i);
            Teacher subTeacher = teacherDao.getTeacherByTeacherID(subCourse.getTeacherID());
            Map<String, Object> subCourseMap = new HashMap<>(3);
            Map<String, Object> masterMap = new HashMap<>(3);
            Map<String, Object> receiveMap = new HashMap<>(3);
            masterMap.put("masterCourseID",courseID);
            masterMap.put("masterCourseName",course.getCourseName());
            masterMap.put("teacherName",teacher.getName());
            receiveMap.put("receiveCourseID",subCourse.getID());
            receiveMap.put("receiveCourseName",subCourse.getCourseName());
            receiveMap.put("teacherName",subTeacher.getName());
            BigInteger shareID = courseMapper.getTeamShareIDByMainAndSubCourseID(courseID, subCourse.getID());
            subCourseMap.put("teamShareID",shareID);
            subCourseMap.put("masterCourse",masterMap);
            subCourseMap.put("receiveCourse",receiveMap);
            courseMapList.add(subCourseMap);
        }
        return courseMapList;
    }

    public BigInteger createTeamShareRequestByCourseID(BigInteger mainCourseID, BigInteger subCourseID) {
        ShareTeamApplication newTeamShare = new ShareTeamApplication();
        newTeamShare.setMainCourseID(mainCourseID);
        newTeamShare.setSubCourseID(subCourseID);
        newTeamShare.setStatus(null);
        newTeamShare.setSubCourseTeacherID(teacherMapper.getTeacherIDByCourseID(subCourseID));
        return teamShareDao.insertTeamShareByTeamShare(newTeamShare);
    }
}
