//package cn.edu.xmu.crms.controller;
//
//import cn.edu.xmu.crms.entity.Student;
//import cn.edu.xmu.crms.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigInteger;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * @author Hongqiwu
// * @date 2018/12/01 15:02
// */
//@RestController
//public class StudentController {
//    @Autowired
//    StudentService studentService;
//
//    @GetMapping("/student")
//    public List<Map<String, Object>> listAllStudentsInfo() {
//        return  studentService.listAllStudentsInfo();
//    }
//
//    @GetMapping("/student/searchstudent")
//    public Map<String, Object> searchStudent() {
//        //用学号或姓名搜索单个学生(用不到)
//        Map<String, Object> map = new HashMap<>(4);
//        map.put("id","id");
//        map.put("account","account");
//        map.put("name","name");
//        map.put("email","email");
//        return  map;
//    }
//
//    @PutMapping("/student/{studentID}/information")
//    public void modifyStudentInfo(@PathVariable("studentID") BigInteger studentID,
//                                  @RequestBody Student student) {
//        student.setID(studentID);
//        studentService.updateStudentInfoByStudent(student);
//    }
//
//    @PutMapping("/student/{studentID}/password")
//    public Map<String, Object> resetStudentPassword(@PathVariable("studentID") BigInteger studentID) {
//        return studentService.resetStudentPasswordByStudentID(studentID);
//    }
//
//    @DeleteMapping("/student/{studentID}")
//    public void deleteStudent(@PathVariable("studentID") BigInteger studentID) {
//        studentService.deleteStudentByStudentID(studentID);
//    }
//
//    @PutMapping("/student/active")
//    public Map<String, Object> activateStudent(@RequestBody Student student) {
//        return studentService.updateStudentActiveByStudentID(student);
//    }
//}
