package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName StudentDao
 * @Author Hongqiwu
 **/

@Repository
public class StudentDao {

    @Autowired
    StudentMapper studentMapper;

    public Student getStudentByStudentID(BigInteger studentID) {
        return studentMapper.getStudentByStudentID(studentID);
    }

    public Integer updateStudentInfo(Student student) {
        return studentMapper.updateStudentInfoByStudent(student);
    }

    public Integer resetStudentPassword(BigInteger studentID) {
        return studentMapper.resetStudentPasswordByStudentID(studentID);
    }

    public Integer deleteStudentByStudentID(BigInteger studentID) {
        return studentMapper.deleteStudentByStudentID(studentID);
    }

    public Integer updateStudentActiveByStudent(Student student) {
        return studentMapper.updateStudentActiveByStudent(student);
    }

    public List<Student> listAllStudents() {
        return studentMapper.listAllStudents();
    }

    public List<Student> listStudentsByCourseAndTeamID(BigInteger courseID, BigInteger teamID) {
        List<Student> students = new ArrayList<>();
        List<BigInteger> studentsID = studentMapper.listStudentsIDByCourseAndTeamID(courseID, teamID);
        for(int i = 0; i < studentsID.size(); i++) {
            students.add(studentMapper.getStudentByStudentID(studentsID.get(i)));
        }
        return students;
    }

    public List<Student> listNoTeamStudentsByCourseID(BigInteger courseID) {
        return studentMapper.listNoTeamStudentsByCourseID(courseID);
    }

}
