package cn.edu.xmu.crms.dao;

import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.mapper.StudentMapper;
import cn.edu.xmu.crms.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SongLingbing
 * @date 2018/12/23 22:11
 */
@Repository
public class UserDao {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;

    /**
      * 根据账号查找对应的用户
      *
      * @param username 账号
      * @return User user
      * @author SongLingbing
      * @date 2018/12/23 22:13
      */
    public User getUserByUsername(String username){
        User user = studentMapper.getStudentByStudentAccount(username);
        if (user==null) {
            user = teacherMapper.getTeacherByTeacherAccount(username);
        }else {
            List<String> roles=new ArrayList<>();
            roles.add("student");
            user.setRoles(roles);
            return user;
        }
        if(user==null){
            return null;
        }else {
            List<String> roles=new ArrayList<>();
            roles.add("teacher");
            user.setRoles(roles);
            return user;
        }
    }

    public void insertUser(User user){
        List<String> roles=user.getRoles();
        String teacherRole = "teacher";
        String studentRole = "student";
        if(roles.contains(teacherRole)){
            teacherMapper.insertTeacher(user);
        }else if(roles.contains(studentRole)){
            studentMapper.insertStudent(user);
        }
    }
}
