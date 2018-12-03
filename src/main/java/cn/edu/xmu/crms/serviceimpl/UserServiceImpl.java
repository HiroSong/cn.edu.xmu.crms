package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.service.UserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:12
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    StudentDao studentDao;
    @Autowired
    TeacherDao teacherDao;
    @Override
    public User getUserByNumber(BigInteger number) {
        User user = studentDao.selectStudentByNumber(number);
        if (user==null) {
            user = teacherDao.selectTeacherByNumber(number);
        }else {
            user.setRole("student");
            return user;
        }
        if(user==null){
            return null;
        }else {
            user.setRole("teacher");
            return user;
        }
    }

    @Override
    public  User getUserByCheck(User user){
        User userByNumber = getUserByNumber(user.getNumber());
        if (userByNumber == null) {
            return null;
        }
        String password = new Sha256Hash(user.getPassword(),
                user.getCredentialsSalt(),
                1024).toBase64();
        System.out.println(password);

        String truePassword = userByNumber.getPassword();
        System.out.println(truePassword);

        if(truePassword.equals(password)){
            return userByNumber;
        }
        return null;
    }
}
