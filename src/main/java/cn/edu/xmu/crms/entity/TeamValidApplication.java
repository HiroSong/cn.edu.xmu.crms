package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
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
    private String requestType;
    private BigInteger courseID;
    private BigInteger klassID;
    private BigInteger leaderID;
    private Team team;
    private Teacher teacher;
    private Course course;
    private Klass klass;
    private Student leader;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    public Student getLeader() {
        return leader;
    }

    public void setLeader(Student leader) {
        this.leader = leader;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public BigInteger getKlassID() {
        return klassID;
    }

    public void setKlassID(BigInteger klassID) {
        this.klassID = klassID;
    }

    public BigInteger getLeaderID() {
        return leaderID;
    }

    public void setLeaderID(BigInteger leaderID) {
        this.leaderID = leaderID;
    }

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
