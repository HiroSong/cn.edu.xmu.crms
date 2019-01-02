package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName CourseMemberLimitStrategy
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/28 20:22
 **/
public class CourseMemberLimitStrategy {
    private BigInteger id;
    private BigInteger courseID;
    private String teacherName;
    private String courseName;
    private Integer minMember;
    private Integer maxMember;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public Integer getMinMember() {
        return minMember;
    }

    public void setMinMember(Integer minMember) {
        this.minMember = minMember;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

}
