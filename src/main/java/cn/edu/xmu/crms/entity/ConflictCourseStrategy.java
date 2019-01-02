package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName ConflictCourseStrategy
 * @Description 课程冲突策略
 * @Author Hongqiwu
 * @Date 2018/12/17 15:43
 **/
public class ConflictCourseStrategy {
    private BigInteger id;
    private BigInteger courseID;
    private String teacherName;
    private String courseName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
}
