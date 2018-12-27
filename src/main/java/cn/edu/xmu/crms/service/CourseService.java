package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.KlassDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Klass;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CourseService
 * @Author Hongqiwu
 **/
@RestController
@Service
public class CourseService {
    @Autowired
    KlassDao klassDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Map<String, Object> getCourseInfo(Course course) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", course.getID());
        map.put("name", course.getCourseName());
        map.put("introduction", course.getIntroduction());
        map.put("presentationWeight", new Double(course.getPresentationPercentage()) / 100.0);
        map.put("questionWeight", new Double(course.getQuestionPercentage()) / 100.0);
        map.put("reportWeight", new Double(course.getReportPercentage()) / 100.0);
        map.put("startTeamTime", course.getTeamStartTime());
        map.put("endTeamTime", course.getTeamEndTime());
        map.put("teacherID", course.getTeacher().getID());
        map.put("teacherUsername", course.getTeacher().getUsername());
        map.put("teacherName", course.getTeacher().getName());
        map.put("minMember",course.getMinMember());
        map.put("maxMember",course.getMaxMember());
        return map;
    }

    @PreAuthorize("hasAnyAuthority('student', 'teacher')")
    @GetMapping("/course")
    public List<Map<String, Object>> listCoursesInfoByStudentOrTeacherID(HttpServletRequest request) {
        BigInteger id = jwtTokenUtil.getIDFromRequest(request);
        String role = jwtTokenUtil.getRolesFromRequest(request);
        List<Map<String, Object>> listCoursesInfo = new ArrayList<>();
        if (JwtTokenUtil.USER_STUDENT.equals(role)) {
            List<Course> courses = courseDao.listCoursesByStudentID(id);
            if (courses == null) {
                return null;
            }
            for (int i = 0; i < courses.size(); i++) {
                Map<String, Object> map = new HashMap<>(3);
                Course course = courses.get(i);
                Klass klass = klassDao.getKlassByStudentAndCourseID(id, course.getID());
                map.put("id",course.getID());
                map.put("courseName", course.getCourseName());
                map.put("klassGrade", klass.getGrade());
                map.put("klassSerial", klass.getKlassSerial());
                listCoursesInfo.add(map);
            }
        } else {
            List<Course> courses = courseDao.listCoursesByTeacherID(id);
            if (courses == null) {
                return null;
            }
            for (int i = 0; i < courses.size(); i++) {
                Map<String, Object> map = new HashMap<>(4);
                Course course = courses.get(i);
                map.put("id", course.getID());
                map.put("courseName", course.getCourseName());
                map.put("isShareTeam", course.getTeamMainCourseID());
                map.put("isShareSeminar", course.getSeminarMainCourseID());
                listCoursesInfo.add(map);
            }
        }
        return listCoursesInfo;
    }


    @GetMapping("/course/{courseID}")
    public Map<String, Object> getCourseInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
        Course course = courseDao.getCourseByCourseID(courseID);
        if (course == null) {
            return null;
        }
        return this.getCourseInfo(course);
    }

    @PostMapping("/course")////////！！！！！！
    public Map<String, Object> createNewCourse(HttpServletRequest request,@RequestBody Course course) {
        BigInteger teacherID = jwtTokenUtil.getIDFromRequest(request);
        Teacher teacher = new Teacher();
        teacher.setID(teacherID);
        course.setTeacher(teacher);
        Map<String, Object> map = new HashMap<>(1);
        map.put("courseID",courseDao.insertCourse(course));
        return map;
    }

    @PreAuthorize("hasAuthority('teacher')")
    @DeleteMapping("/course/{courseID}")
    public void deleteCourseByCourseID(@PathVariable("courseID") BigInteger courseID) {
        courseDao.deleteCourseInfoByCourseID(courseID);
    }
}
