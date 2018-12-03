package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:34
 */
public class Seminar {
    BigInteger id;

    String seminarName;
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getSeminarRequire() {
        return seminarRequire;
    }

    public void setSeminarRequire(String seminarRequire) {
        this.seminarRequire = seminarRequire;
    }

    public BigInteger getSeminarOrder() {
        return seminarOrder;
    }

    public void setSeminarOrder(BigInteger seminarOrder) {
        this.seminarOrder = seminarOrder;
    }

    public boolean isSeminarSeen() {
        return seminarSeen;
    }

    public void setSeminarSeen(boolean seminarSeen) {
        this.seminarSeen = seminarSeen;
    }

    public String getSeminarStartTime() {
        return seminarStartTime;
    }

    public void setSeminarStartTime(String seminarStartTime) {
        this.seminarStartTime = seminarStartTime;
    }

    public BigInteger getSeminarState() {
        return seminarState;
    }

    public void setSeminarState(BigInteger seminarState) {
        this.seminarState = seminarState;
    }

    public BigInteger getTeamLimit() {
        return teamLimit;
    }

    public void setTeamLimit(BigInteger teamLimit) {
        this.teamLimit = teamLimit;
    }

    public BigInteger getRound() {
        return round;
    }

    public void setRound(BigInteger round) {
        this.round = round;
    }

    public boolean getRegistOrder() { return registOrder; }

    public void setRegistOrder(boolean registOrder) {
        this.registOrder = registOrder;
    }

    String seminarRequire;
    Integer seminarOrder;
    boolean seminarSeen;
    String seminarStartTime;
    Integer seminarState;   //讨论课未开始/进行中/结束状态
    Integer teamLimit;
    Integer round;
    boolean registOrder; //是否可以自定讨论课报名顺序
}
