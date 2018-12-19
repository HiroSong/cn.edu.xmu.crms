package cn.edu.xmu.crms.serviceimpl;

import cn.edu.xmu.crms.dao.StudentDao;
import cn.edu.xmu.crms.dao.TeacherDao;
import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.service.UserService;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getUserByCheck(User user) throws NoSuchAlgorithmException {
        User userByNumber = getUserByNumber(user.getNumber());
        if (userByNumber == null) {
            return null;
        }
        String password = encodeString(user.getPassword()+user.getCredentialsSalt());
        String truePassword = userByNumber.getPassword();
        if(truePassword.equals(password)){
            String token = encodeString(userByNumber.getCredentialsSalt());
            writeSession(token);
            Map<String, Object> map = new HashMap<>(3);
            map.put("token",token);
            map.put("id",userByNumber.getId());
            map.put("role",userByNumber.getRole());
            return map;
        }
        return null;
    }
    @Override
    public String encodeString(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(string.getBytes("UTF-8"));
            encodeStr = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw e;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    @Override
    public void writeSession(String token){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        request.getSession().setAttribute("token",token);
    }
}
