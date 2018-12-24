package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:31
 */
public class User {
    protected BigInteger id;
    protected String username;
    protected String password;
    protected Integer beActive;
    protected String email;
    protected String role;
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBeActive() {
        return beActive;
    }

    public void setBeActive(Integer beActive) {
        this.beActive = beActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
