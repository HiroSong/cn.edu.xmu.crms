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
