package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.entity.User;
import cn.edu.xmu.crms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:03
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) throws NoSuchAlgorithmException {
        return userService.getUserByCheck(user);
    }
}
