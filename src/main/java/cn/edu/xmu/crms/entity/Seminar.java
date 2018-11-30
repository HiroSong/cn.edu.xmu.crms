package cn.edu.xmu.crms.entity;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:34
 */
public class Seminar {
    String seminarName;

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

    public int getSeminarOrder() {
        return seminarOrder;
    }

    public void setSeminarOrder(int seminarOrder) {
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

    public int getSeminarState() {
        return seminarState;
    }

    public void setSeminarState(int seminarState) {
        this.seminarState = seminarState;
    }

    public int getTeamLimit() {
        return teamLimit;
    }

    public void setTeamLimit(int teamLimit) {
        this.teamLimit = teamLimit;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRegistOrder() {
        return registOrder;
    }

    public void setRegistOrder(int registOrder) {
        this.registOrder = registOrder;
    }

    String seminarRequire;
    int seminarOrder;
    boolean seminarSeen;
    String seminarStartTime;
    int seminarState;
    int teamLimit;
    int round;
    int registOrder;
}
