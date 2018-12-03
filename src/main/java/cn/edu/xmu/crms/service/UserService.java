package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.User;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 15:05
 */
public interface UserService {
    /**
      * 用于根据用户号码查找用户并返回用户实例和角色类型
      *
      * @param number 用户号码
      * @return User 返回查找到的对象，若无记录则为null
      * @author SongLingbing
      * @date 2018/11/30 8:43
      */
    public User getUserByNumber(BigInteger number);

    /**
      * 用于验证登录
      *
      * @param user 用户实例
      * @return User 账户密码正确则返回对象，否则为null
      * @author SongLingbing
      * @date 2018/11/30 12:37
      */
    public User getUserByCheck(User user);
}
