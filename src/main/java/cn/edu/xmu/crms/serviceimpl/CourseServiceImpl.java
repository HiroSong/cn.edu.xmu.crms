package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.DeadlineDao;
import cn.edu.xmu.crms.entity.Course;
import cn.edu.xmu.crms.entity.Deadline;
import cn.edu.xmu.crms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hongqiwu
 * @date 2018/11/30 19:53
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseDao courseDao;
    @Autowired
    DeadlineDao deadlineDao;
    @Override
    public Course getCourseByCourseId(BigInteger id) {
        Course course = courseDao.selectCourseByCourseId(id);
        return course;
    }
    @Override
    public Deadline getCourseDeadlineByCourseId(BigInteger id) {
        BigInteger deadlineId = deadlineDao.selectDeadlineIdByCourseId(id);
        Deadline deadline = deadlineDao.selectCourseDeadlineById(deadlineId);
        return deadline;
    }
    @Override
    public List<Course> getCourseByTeacherId(BigInteger id) {
        List<BigInteger> courseId = courseDao.selectCourseIdByTeacherId(id);
        List<Course> course = new ArrayList<>();
        for(int i = 0; i < courseId.size(); i++) {
            course.add(courseDao.selectCourseByCourseId(courseId.get(i)));
        }
        return course;
    }
    @Override
    public List<Course> getCourseByStudentId(BigInteger id) {
        List<BigInteger> courseId = courseDao.selectCourseIdByStudentId(id);
        List<Course> course = new ArrayList<>();
        for(int i = 0; i < courseId.size(); i++) {
            course.add(courseDao.selectCourseByCourseId(courseId.get(i)));
        }
        return course;
    }
}
