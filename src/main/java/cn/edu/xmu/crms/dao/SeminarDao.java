package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.mapper.CourseMapper;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StudentDao
 * @Author Hongqiwu
 **/

@Repository
public class SeminarDao{
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    CourseMapper courseMapper;

    public List<Course> listMainCoursesByCourseID(BigInteger courseID) {
        List<BigInteger> mainCoursesIDList = seminarMapper.listMainCoursesIDByCourseID(courseID);
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
        List<BigInteger> subCoursesIDList = seminarMapper.listSubCoursesIDByCourseID(courseID);
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
