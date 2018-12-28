package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.CourseDao;
import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeamDao;
import cn.edu.xmu.crms.dao.TeamValidDao;
import cn.edu.xmu.crms.entity.*;
import cn.edu.xmu.crms.mapper.*;
import cn.edu.xmu.crms.util.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamService
 * @Author Hongqiwu
 **/
@Service
public class TeamService {
    public void deleteStudentFromTeamByTeamAndStudentID(BigInteger teamID, BigInteger studentID) {
        teamMapper.deleteStudentFromTeamByTeamAndStudentID(teamID,studentID);
        Student student=studentDao.getStudentByStudentID(studentID);
        Team team=teamDao.getTeamByTeamID(teamID);
        String text=student.getName()+"同学，你已离开"+team.getTeamName()+"小组。";
        Email email=new Email();
        email.sendSimpleMail(student.getEmail(),text);
    }
}
