package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.SeminarDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.mapper.SeminarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SeminarService
 * @Author Hongqiwu
 **/
@Service
public class SeminarService {

    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    @Autowired
    SeminarMapper seminarMapper;
    @Autowired
    SeminarDao seminarDao;

    //未完成
    public List<Map<String, Object>> listSeminarScores(BigInteger studentID, BigInteger courseID) {
        List<Map<String, Object>> listScoresInfo = new ArrayList<>();
        return listScoresInfo;
    }
}
