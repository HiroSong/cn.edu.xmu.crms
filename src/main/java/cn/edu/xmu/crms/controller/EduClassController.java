package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.EduClass;
import cn.edu.xmu.crms.entity.Student;
import cn.edu.xmu.crms.service.EduClassService;
import cn.edu.xmu.crms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:04
 */
@RestController
@RequestMapping("/api")
public class EduClassController {
    @Autowired
    EduClassService educlassService;
    @Autowired
    StudentService studentService;
    @GetMapping("/courses/{courseID}/classes")
    public Map<String, Object> getClassList(@PathVariable("courseID")
                                            String courseID){
        BigInteger id = new BigInteger(courseID);
        ArrayList<EduClass> eduClass = educlassService.getEduClassByCourseId(id);
        if(eduClass == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>(eduClass.size());
        for(int i = 0; i < eduClass.size(); i++) {
            Map<String, Object> classlist = new HashMap<>(4);
            classlist.put("classID",eduClass.get(i).getId());
            classlist.put("classTime",eduClass.get(i).getClassTime());
            classlist.put("classAddress",eduClass.get(i).getClassAddress());
            ArrayList<Student> studentList = studentService.getStudentByClassId(eduClass.get(i).getId());
            classlist.put("studentList",studentList);
            map.put("classList"+i,classlist);
        }
        return map;
    }
}
