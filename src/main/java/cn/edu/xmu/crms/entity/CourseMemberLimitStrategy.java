package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName CourseMemberLimitStrategy
 * @Description 课程成员限制策略
 * @Author Hongqiwu
 * @Date 2018/12/17 15:46
 **/
public class CourseMemberLimitStrategy {
    private BigInteger id;
    private BigInteger courseID;
    private Integer minMember;
    private Integer maxMember;

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
