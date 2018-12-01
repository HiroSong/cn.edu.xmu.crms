package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Hongqiwu
 * @date 2018/12/01 14:44
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentDao studentDao;
    @Override
    public ArrayList<Student> getStudentByClassId(BigInteger id) {
        ArrayList<BigInteger> studentId = studentDao.selectStudentIdByClassId(id);
        if(studentId == null) {
            return null;
        }
        ArrayList<Student> students = new ArrayList<>();
        if(students == null) {
            return null;
        }
        for(int i = 0; i < studentId.size(); i++) {
            students.add(studentDao.selectStudentByStudentId(studentId.get(i)));
        }
        return students;
    }
    @Override
    public Student getStudentByStudentId(BigInteger id) {
        Student student = studentDao.selectStudentByStudentId(id);
        if(student == null) {
            return null;
        }
        return student;
    }
}
