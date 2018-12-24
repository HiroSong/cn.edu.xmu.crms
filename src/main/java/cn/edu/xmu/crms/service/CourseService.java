package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseService
 * @Author Hongqiwu
 **/
@Service
public class CourseService {
    @Autowired
    KlassDao klassDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherDao teacherDao;

    /**
     * 用于studentID查找课程列表信息包括课程name 班级name
     *
     * @param id 学生号码
     * @return List<Map<String, Object>> 返回查找到的列表，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
   public List<Map<String, Object>> listCoursesInfoByStudentOrTeacherID(BigInteger id) {
       List<Map<String, Object>> listCoursesInfo = new ArrayList<>();
       //如果是studentID
       if(id.equals(0)) {
           id = new BigInteger("1");
           List<Course> courses = courseDao.listCoursesByStudentID(id);
           if(courses == null) {
               return null;
           }
           for(int i = 0; i < courses.size(); i++) {
               Map<String, Object> map = new HashMap<>(3);
               Course course = courses.get(i);
               Klass klass = klassDao.getKlassByStudentAndCourseID(id, course.getID());
               map.put("courseName",course.getCourseName());
               map.put("klassGrade",klass.getGrade());
               map.put("klassSerial",klass.getKlassSerial());
               listCoursesInfo.add(map);
           }
       }
       else {
           List<Course> courses = courseDao.listCoursesByTeacherID(id);
           if(courses == null) {
               return null;
           }
           for(int i = 0; i < courses.size(); i++) {
               Map<String, Object> map = new HashMap<>(4);
               Course course = courses.get(i);
               Klass klass = klassDao.getKlassByStudentAndCourseID(id, course.getID());
               map.put("id",course.getID());
               map.put("name",course.getCourseName());
               map.put("isShareTeam",course.getID().equals(course.getTeamMainCourseID()));
               map.put("isShareSeminar",course.getID().equals(course.getSeminarMainCourseID()));
               listCoursesInfo.add(map);
           }
       }
       return listCoursesInfo;
    }


    /**
     * 用于courseID查找课程信息
     *
     * @param courseID 课程号码
     * @return Map<String, Object> 返回查找到的信息，若无记录则为null
     * @author Hongqiwu
     * @date 2018/11/30 19:41
     */
    public Map<String, Object> getCourseInfoByCourseID(BigInteger courseID) {
        Map<String, Object> map = new HashMap<>(8);
        Course course = courseDao.getCourseByCourseID(courseID);
        if(course == null) {
            return null;
        }
        map.put("introduction",course.getIntroduction());
        map.put("presentationWeight",course.getPresentationPercentage());
        map.put("questionWeight",course.getQuestionPercentage());
        map.put("reportWeight",course.getReportPercentage());
        map.put("startTeamTime",course.getTeamStartTime());
        map.put("endTeamTime",course.getTeamEndTime());
        map.put("minMemberNumber",courseMapper.getCourseMinMemberByCourseID(courseID));
        map.put("maxMemberNumber",courseMapper.getCourseMaxMemberByCourseID(courseID));
        return map;
    }

    public BigInteger createNewCourse(Course course) {
        return courseDao.insertCourseByCourse(course);
    }
}
