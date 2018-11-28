package cn.edu.xmu.crms.controller;

import cn.edu.xmu.crms.dao.UserMapper;
import cn.edu.xmu.crms.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by beyondLi on 2017/6/19.
 */
//证明是controller层并且返回json
@RestController
public class UserController {
    //依赖注入
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/cs")
    public User cs() {
        //调用dao层
        User user = userMapper.selectUserByName("beyondLi");
        return user;
    }
}