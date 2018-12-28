package cn.edu.xmu.crms.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName Course
 * @Author Hongqiwu
 * @Description 课程
 **/
public class Course {
    private BigInteger id;
    private BigInteger teacherID;
    private String courseName;
    private String introduction;
    private Integer presentationPercentage;
    private Integer questionPercentage;
    private Integer reportPercentage;
    private Timestamp teamStartTime;
    private Timestamp teamEndTime;
    private BigInteger teamMainCourseID;
    private BigInteger seminarMainCourseID;
    private Integer minMemberNumber;
    private Integer maxMemberNumber;
    private List<ConflictCourse> conflictCourses;
    private Teacher teacher;


    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(BigInteger teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPresentationPercentage() {
        return presentationPercentage;
    }

    public void setPresentationPercentage(Integer presentationPercentage) {
        this.presentationPercentage = presentationPercentage;
    }

    public Integer getQuestionPercentage() {
        return questionPercentage;
    }

    public void setQuestionPercentage(Integer questionPercentage) {
        this.questionPercentage = questionPercentage;
    }

    public Integer getReportPercentage() {
        return reportPercentage;
    }

    public void setReportPercentage(Integer reportPercentage) {
        this.reportPercentage = reportPercentage;
    }

    public Timestamp getTeamStartTime() {
        return teamStartTime;
    }

    public void setTeamStartTime(Timestamp teamStartTime) {
        this.teamStartTime = teamStartTime;
    }

    public Timestamp getTeamEndTime() {
        return teamEndTime;
    }

    public void setTeamEndTime(Timestamp teamEndTime) {
        this.teamEndTime = teamEndTime;
    }

    public BigInteger getTeamMainCourseID() {
        return teamMainCourseID;
    }

    public void setTeamMainCourseID(BigInteger teamMainCourseID) {
        this.teamMainCourseID = teamMainCourseID;
    }

    public BigInteger getSeminarMainCourseID() {
        return seminarMainCourseID;
    }

    public void setSeminarMainCourseID(BigInteger seminarMainCourseID) {
        this.seminarMainCourseID = seminarMainCourseID;
    }

    public Integer getMinMember() {
        return minMemberNumber;
    }

    public void setMinMember(Integer minMemberNumber) {
        this.minMemberNumber = minMemberNumber;
    }

    public Integer getMaxMember() {
        return maxMemberNumber;
    }

    public void setMaxMember(Integer maxMemberNumber) {
        this.maxMemberNumber = maxMemberNumber;
    }

    public List<ConflictCourse> getConflictCourses() {
        return conflictCourses;
    }

    public void setConflictCourses(List<ConflictCourse> conflictCourses) {
        this.conflictCourses = conflictCourses;
    }
}
