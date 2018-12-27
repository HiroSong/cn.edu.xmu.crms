package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName Question
 * @Description 提问
 * @Author Hongqiwu
 * @Date 2018/12/17 15:02
 **/
public class Question {
    private BigInteger id;
    private BigInteger klssSeminarID;
    private BigInteger attendanceID;
    private BigInteger studentID;
    private BigInteger teamID;
    private Integer beSelected;
    private String score;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger ID) {
        this.id = id;
    }

    public BigInteger getKlssSeminarID() {
        return klssSeminarID;
    }

    public void setKlssSeminarID(BigInteger klssSeminarID) {
        this.klssSeminarID = klssSeminarID;
    }

    public BigInteger getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(BigInteger attendanceID) {
        this.attendanceID = attendanceID;
    }

    public BigInteger getStudentID() {
        return studentID;
    }

    public void setStudentID(BigInteger studentID) {
        this.studentID = studentID;
    }

    public Integer getBeSelected() {
        return beSelected;
    }

    public void setBeSelected(Integer beSelected) {
        this.beSelected = beSelected;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public BigInteger getTeamID() { return teamID; }

    public void setTeamID(BigInteger teamID) { this.teamID = teamID; }

}
