package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author 75
 * @date 2018/11/29
 */
public class EduClass {
    private BigInteger id;
    private Integer classNumber;
    private String classTime;
    private String classAddress;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassAddress() {
        return classAddress;
    }

    public void setClassAddress(String classAddress) {
        this.classAddress = classAddress;
    }
}
