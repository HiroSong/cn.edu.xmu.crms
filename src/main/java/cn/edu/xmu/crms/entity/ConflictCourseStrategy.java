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
    private BigInteger course1ID;
    private BigInteger course2ID;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getCourse1ID() {
        return course1ID;
    }

    public void setCourse1ID(BigInteger course1ID) {
        this.course1ID = course1ID;
    }

    public BigInteger getCourse2ID() {
        return course2ID;
    }

    public void setCourse2ID(BigInteger course2ID) {
        this.course2ID = course2ID;
    }
}
