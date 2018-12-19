package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.KlassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:45
 */
@Repository
public class CourseDao {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    KlassMapper klassMapper;

    public Course getCourseByCourseID(BigInteger courseID) {
        return courseMapper.getCourseByCourseID(courseID);
    }

    public List<Course> listCoursesByStudentID(BigInteger studentID) {
        List<Course> courses = new ArrayList<>();
        List<BigInteger> allCourseID = courseMapper.listCourseIDByStudentID(studentID);
        if(allCourseID == null) {
            return null;
        }
        for(int i = 0; i < allCourseID.size(); i++) {
            Course course = courseMapper.getCourseByCourseID(allCourseID.get(i));
            courses.add(course);
        }
        return courses;
    }

    public void deleteCourseInfoByCourseID(BigInteger courseID) {
        courseMapper.deleteCourseByCourseID(courseID);
        courseMapper.deleteConflictCourseStrategyByCourseID(courseID);
        courseMapper.deleteCourseInKlassByCourseID(courseID);
        courseMapper.deleteCourseInKlassStudentByCourseID(courseID);
        courseMapper.deleteCourseInRoundByCourseID(courseID);
        courseMapper.deleteCourseInSeminarByCourseID(courseID);
        courseMapper.deleteCourseInTeamByCourseID(courseID);
        courseMapper.deleteCourseInTeamStrategyByCourseID(courseID);
        courseMapper.deleteCourseMemberLimitStrategyByCourseID(courseID);
    }

    public BigInteger insertCourseByCourse(Course course) {
        return courseMapper.insertCourseByCourse(course);
    }

    public List<Course> listMainCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> mainCoursesIDList = courseMapper.listMainCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(mainCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < mainCoursesIDList.size(); i++) {
            BigInteger mainCourseID = mainCoursesIDList.get(i);
            Course course = courseMapper.getCourseByCourseID(mainCourseID);
            courses.add(course);
        }
        return courses;
    }

    public List<Course> listSubCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> subCoursesIDList = courseMapper.listSubCoursesIDByCourseID(courseID);
        List<Course> courses = new ArrayList<>();
        if(subCoursesIDList == null) {
            return null;
        }
        for(int i = 0; i < subCoursesIDList.size(); i++) {
            BigInteger subCourseID = subCoursesIDList.get(i);
            Course course = courseMapper.getCourseByCourseID(subCourseID);
            courses.add(course);
        }
        return courses;
    }
}
