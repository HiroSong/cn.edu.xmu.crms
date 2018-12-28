package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.UserDao;
import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/12/24 9:02
 */
@CrossOrigin
@RestController
@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     *
     * @param user 用户
     * @return 操作结果
     */
    @PostMapping("/user/login")
    public Map<String, Object> login(@RequestBody User user) throws AuthenticationException {
        String username = user.getUsername();
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userInDatabase = userDao.getUserByUsername(username);
        String token = jwtTokenUtil.generateToken(userInDatabase);
        Map<String, Object> map = new HashMap<>(3);
        map.put("token", token);
        map.put("id", userInDatabase.getID());
        map.put("role", userInDatabase.getRoles().get(0));
        map.put("beActive", userInDatabase.getBeActive());
        return map;
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping("/user/register")
    public String register(@RequestBody User user) {
        String username = user.getUsername();
        User userInDatabase = userDao.getUserByUsername(username);
        if (userInDatabase != null) {
            return "用户已存在";
        }
        String password = user.getPassword();
        user.setPassword(password);
        userDao.insertUser(user);
        return "success";
    }

    @GetMapping("/user/information")
    public User getUserInfo(HttpServletRequest request) {
        BigInteger id = jwtTokenUtil.getIDFromRequest(request);
        String role = jwtTokenUtil.getRolesFromRequest(request);
        return userDao.getUserByInfo(id,role);
    }

    @PutMapping("/user/password")//修改密码
    public void modifyPassword(@RequestBody User user, HttpServletRequest request) {
        BigInteger id = jwtTokenUtil.getIDFromRequest(request);
        String role = jwtTokenUtil.getRolesFromRequest(request);
        user.setID(id);
        userDao.updateUserPassword(user,role);
    }
}
