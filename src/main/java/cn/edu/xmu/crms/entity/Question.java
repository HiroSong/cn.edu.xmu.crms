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
    private Attendance attendance;
    private Student student;
    private Team team;
    private Integer beSelected;
    private Double score;
    public Integer order;

    public Question(){
        attendance=new Attendance();
        student=new Student();
        team=new Team();
    }
    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public void setTeamID(BigInteger teamID){this.team.setID(teamID);}

    public BigInteger getKlssSeminarID() {
        return klssSeminarID;
    }

    public void setKlssSeminarID(BigInteger klssSeminarID) {
        this.klssSeminarID = klssSeminarID;
    }

    public BigInteger getAttendanceID() {
        return attendance.getID();
    }

    public void setAttendanceID(BigInteger attendanceID) {
        this.attendance.setID(attendanceID);
    }

    public BigInteger getStudentID() {
        return student.getID();
    }

    public void setStudentID(BigInteger studentID) {
        this.student.setID(studentID);
    }

    public Integer getBeSelected() {
        return beSelected;
    }

    public void setBeSelected(Integer beSelected) {
        this.beSelected = beSelected;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public BigInteger getTeamID() { return team.getID(); }

    public Team getTeam(){return this.team;}

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
