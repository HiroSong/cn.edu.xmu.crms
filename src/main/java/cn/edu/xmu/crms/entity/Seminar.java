package cn.edu.xmu.crms.entity;


import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @ClassName Seminar
 * @Description 讨论课
 * @Author Hongqiwu
 * @Date 2018/12/17 15:02
 **/
public class Seminar {
    private BigInteger id;
    private BigInteger courseID;
    private BigInteger roundID;
    private String seminarName;
    private String introduction;
    private Integer maxTeam;
    private Integer beVisible;
    private Integer seminarSerial;
    private Integer roundOrder;
    private Timestamp enrollStartTime;
    private Timestamp enrollEndTime;

    public Integer getRoundOrder() {
        return roundOrder;
    }

    public void setRoundOrder(Integer roundOrder) {
        this.roundOrder = roundOrder;
    }
    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public BigInteger getRoundID() {
        return roundID;
    }

    public void setRoundID(BigInteger roundID) {
        this.roundID = roundID;
    }

    public String getSeminarName() {
        return seminarName;
    }

    public void setSeminarName(String seminarName) {
        this.seminarName = seminarName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getMaxTeam() {
        return maxTeam;
    }

    public void setMaxTeam(Integer maxTeam) {
        this.maxTeam = maxTeam;
    }

    public Integer getBeVisible() {
        return beVisible;
    }

    public void setBeVisible(Integer beVisible) {
        this.beVisible = beVisible;
    }

    public Integer getSeminarSerial() {
        return seminarSerial;
    }

    public void setSeminarSerial(Integer seminarSerial) {
        this.seminarSerial = seminarSerial;
    }

    public Timestamp getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(Timestamp enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public Timestamp getEnrollEndTime() {
        return enrollEndTime;
    }

    public void setEnrollEndTime(Timestamp enrollEndTime) {
        this.enrollEndTime = enrollEndTime;
    }
}
