package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.dao.UserDao;
import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.util.email.Email;
import cn.edu.xmu.crms.util.security.JwtTokenUtil;
import cn.edu.xmu.crms.util.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{userName}/password")
    public Map<String,Object> retrievePassWord(@PathVariable("userName") String userName){
        User userInDatabase = userDao.getUserByUsername(userName);
        Map<String,Object> map=new HashMap<>();
        if(userInDatabase==null) {
            map.put("result","user"+userName+" not found.");
        } else{
            System.out.print("\n"+userInDatabase.getName()+userInDatabase.getEmail()+'\n');
            Email email=new Email();
            email.sendPassWordMail(userInDatabase.getEmail(),userInDatabase.getPassword());
            map.put("result","password has been sent to Mail.");
        }
        return map;
    }

}
