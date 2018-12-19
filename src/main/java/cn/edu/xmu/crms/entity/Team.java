package cn.edu.xmu.crms.entity;
import java.math.BigInteger;

/**
 * @ClassName Team
 * @Author Hongqiwu
 * @Description 组队
 **/
public class Team {
    private BigInteger id;
    private BigInteger klassID;
    private BigInteger courseID;
    private BigInteger leaderID;
    private String teamName;
    private Integer teamSerial;
    private Integer status;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getKlassID() {
        return klassID;
    }

    public void setKlassID(BigInteger klassID) {
        this.klassID = klassID;
    }

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public BigInteger getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(BigInteger leaderID) {
        this.leaderID = leaderID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamSerial() {
        return teamSerial;
    }

    public void setTeamSerial(Integer teamSerial) {
        this.teamSerial = teamSerial;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
