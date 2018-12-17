package cn.edu.xmu.crms.service;

import cn.edu.xmu.crms.entity.User;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

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
    User getUserByNumber(BigInteger number);
    /**
      * 用于验证登录
      *
      * @param user 用户实例
      * @return Map 账户密码正确则返回结果，否则为null
      * @exception  NoSuchAlgorithmException 找不到对应加密算法时返回异常
      * @author SongLingbing
      * @date 2018/11/30 12:37
      */
    Map<String, Object> getUserByCheck(User user) throws NoSuchAlgorithmException;

    /**
      * 用于加密字符串
      *
      * @param string 待加密的字符串
      * @return String 加密后的字符串
     * @exception  NoSuchAlgorithmException 找不到对应加密算法时返回异常
     * @author SongLingbing
      * @date 2018/11/30 23:50
      */
    String encodeString(String string) throws NoSuchAlgorithmException;

    /**
      * 用于将身份信息添加到session属性
      *
      * @param token 令牌字符串
      * @return void
      * @author SongLingbing
      * @date 2018/12/1 0:22
      */
    void writeSession(String token);
}
