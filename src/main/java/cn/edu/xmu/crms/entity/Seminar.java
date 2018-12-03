package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:34
 */
public class Seminar {
    String seminarName;
    String seminarRequire;
    BigInteger seminarOrder;
    boolean seminarSeen;
    String seminarStartTime;
    BigInteger seminarState;
    BigInteger teamLimit;
    BigInteger round;
    BigInteger registOrder;

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

    public BigInteger getRegistOrder() {
        return registOrder;
    }

    public void setRegistOrder(BigInteger registOrder) {
        this.registOrder = registOrder;
    }


}
