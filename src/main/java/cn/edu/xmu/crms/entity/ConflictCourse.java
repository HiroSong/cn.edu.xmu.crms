package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName ConflictCourse
 * @Description 冲突课程信息
 * @Author Hongqiwu
 * @Date 2018/12/19 22:49
 **/
public class ConflictCourse {
    private BigInteger courseID;
    private String courseName;
    private BigInteger teacherID;
    private String teacherName;

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public BigInteger getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(BigInteger teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
