package cn.edu.xmu.crms.dao;


import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.mapper.StudentMapper;
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
public class StudentDao {
    @Autowired
    StudentMapper studentMapper;

    public Student getStudentByStudentID(BigInteger studentID) {
        return studentMapper.getStudentByStudentID(studentID);
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
        List<Student> students = new ArrayList<>();
        List<BigInteger> noTeamStudentsID = studentMapper.listNoTeamStudentsIDByCourseID(courseID);
        for(int i = 0; i < noTeamStudentsID.size(); i++) {
            Student noTeamStudent = studentMapper.getStudentByStudentID(noTeamStudentsID.get(i));
            students.add(noTeamStudent);
        }
        return students;
    }
}
