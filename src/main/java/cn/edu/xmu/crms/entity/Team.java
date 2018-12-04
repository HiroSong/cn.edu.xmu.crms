package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author SongLingbing
 * @date 2018/11/29 10:34
 */
public class Team {


    BigInteger id;
    String teamName;
    String teamNumber;
    BigInteger teamLeader;
    String teamPPTName;
    int state;

    public BigInteger getID() { return id; }

    public void setID(BigInteger id) { this.id = id; }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(String teamNumber) {
        this.teamNumber = teamNumber;
    }

    public BigInteger getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(BigInteger teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getTeamPPTName() {
        return teamPPTName;
    }

    public void setTeamPPTName(String teamPPTName) {
        this.teamPPTName = teamPPTName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
