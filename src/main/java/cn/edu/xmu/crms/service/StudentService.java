package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StudentService
 * @Description 关于学生模块的操作
 * @Author Hongqiwu
 * @Date 2018/12/20 4:05
 **/

@RestController
@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;

    @GetMapping("/student/{studentID}")
    public Student getStudentInfo(@PathVariable("studentID") BigInteger studentID) {
        return studentDao.getStudentByStudentID(studentID);
    }


    @GetMapping("/student")
    public List<Map<String, Object>> listAllStudentsInfo() {
        List<Map<String, Object>> studentsInfoList = new ArrayList<>();
        List<Student> students = studentDao.listAllStudents();
        for(int i = 0; i < students.size(); i++) {
            Map<String, Object> map = new HashMap<>(4);
            Student student = students.get(i);
            map.put("id",student.getID());
            map.put("account",student.getUsername());
            map.put("name",student.getName());
            map.put("email",student.getEmail());
            studentsInfoList.add(map);
        }
        return studentsInfoList;
    }

    @PutMapping("/student/{studentID}/information")
    public Student modifyStudentInfo(@PathVariable("studentID") BigInteger studentID,
                                     @RequestBody Student student) {
        student.setID(studentID);
        if(studentDao.updateStudentInfo(student) == 1) {
            return student;
        }
        return null;
    }

    @PutMapping("/student/{studentID}/password")
    public Boolean resetStudentPassword(@PathVariable("studentID") BigInteger studentID) {
        if (studentDao.resetStudentPassword(studentID) == 1) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/student/{studentID}")
    public Boolean deleteStudent(@PathVariable("studentID") BigInteger studentID) {
        if(studentDao.deleteStudentByStudentID(studentID) == 1) {
            return true;
        }
        return false;
    }

    @PutMapping("/student/active")
    public Boolean activateStudent(@RequestBody Student student) {
        if(studentDao.updateStudentActiveByStudent(student) == 1) {
            return true;
        }
        return false;
    }

    @GetMapping("/course/{courseID}/noTeam")
    public List<Map<String, Object>> listNoTeamStudentsInfoByCourseID(@PathVariable("courseID") BigInteger courseID) {
        List<Map<String, Object>> noTeamStudentsMap = new ArrayList<>();
        List<Student> noTeamStudents = studentDao.listNoTeamStudentsByCourseID(courseID);
        for(int i = 0; i < noTeamStudents.size(); i++) {
            Student noTeamStudent = noTeamStudents.get(i);
            Map<String, Object> noTeamStudentMap = new HashMap<>(3);
            noTeamStudentMap.put("id",noTeamStudent.getID());
            noTeamStudentMap.put("account",noTeamStudent.getUsername());
            noTeamStudentMap.put("name",noTeamStudent.getName());
            noTeamStudentsMap.add(noTeamStudentMap);
        }
        return noTeamStudentsMap;
    }

}

