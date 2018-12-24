package cn.edu.xmu.crms.util.security;

import cn.edu.xmu.crms.entity.User;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

/**
 * @author SongLingbing
 * @date 2018/12/24 8:32
 */
@Component
public class JwtTokenUtil implements Serializable {

    /**
     * 密钥
     */
    private final String secret = "Jingli@ooad";

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Map<String, Object> header = new HashMap<>(2);
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        Date expirationDate = new Date(System.currentTimeMillis() + 2592000L * 1000);
        return Jwts.builder().setHeader(header).setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param user 用户
     * @return 令牌
     */
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put("sub", user.getUsername());
        claims.put("id", user.getID());
        claims.put("scope", JSON.toJSON(user.getRoles()));
        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从令牌中获取id
     *
     * @param token 令牌
     * @return 用户ID
     */
    public BigInteger getIDFromToken(String token) {
        BigInteger id;
        try {
            Claims claims = getClaimsFromToken(token);
            id = (BigInteger) claims.get("id");
        } catch (Exception e) {
            id = null;
        }
        return id;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 验证令牌
     *
     * @param token  令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
