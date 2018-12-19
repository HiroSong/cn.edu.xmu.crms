package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName TeamValidApplication
 * @Description 组队请求申请
 * @Author Hongqiwu
 * @Date 2018/12/17 15:32
 **/
public class TeamValidApplication {
    private BigInteger id;
    private BigInteger teamID;
    private BigInteger teacherID;
    private String reason;
    private Integer status;


    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTeamID() {
        return teamID;
    }

    public void setTeamID(BigInteger teamID) {
        this.teamID = teamID;
    }

    public BigInteger getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(BigInteger teacherID) {
        this.teacherID = teacherID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
