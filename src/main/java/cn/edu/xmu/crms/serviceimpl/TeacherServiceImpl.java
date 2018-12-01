package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.Teacher;
import cn.edu.xmu.crms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/12/01 15:11
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherDao teacherDao;
    @Override
    public Teacher getTeacherByTeacherId(BigInteger id) {
        Teacher teacher = teacherDao.selectTeacherByTeacherId(id);
        if(teacher == null) {
            return null;
        }
        return teacher;
    }
}
