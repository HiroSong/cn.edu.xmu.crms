package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName Admin
 * @Author Hongqiwu
 * @Description 管理员
 **/
public class Admin {
    private BigInteger id;
    private String account;
    private String password;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
