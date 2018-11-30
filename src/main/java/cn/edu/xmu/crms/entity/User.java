package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:31
 */
public class User {
    protected BigInteger id;
    protected BigInteger number;
    protected String name;
    protected String email;
    protected String password;
    protected Integer noticeGap;
    protected String role;
    private static final String SALT = "CrmsJingLiProject";

    public BigInteger getId() {
        return id;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNoticeGap() {
        return noticeGap;
    }

    public void setNoticeGap(Integer noticeGap) {
        this.noticeGap = noticeGap;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCredentialsSalt(){
        return number+SALT;
    }
}
