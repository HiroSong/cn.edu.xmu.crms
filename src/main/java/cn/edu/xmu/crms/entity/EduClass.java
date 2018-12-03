package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @author Hongqiwu
 * @date 2018/11/29
 */
public class EduClass {
    private BigInteger id;
    private Integer number;
    private String time;
    private String address;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Integer getClassNumber() {
        return number;
    }

    public void setClassNumber(Integer classNumber) {
        this.number = classNumber;
    }

    public String getClassTime() {
        return time;
    }

    public void setClassTime(String classTime) {
        this.time = classTime;
    }

    public String getClassAddress() {
        return address;
    }

    public void setClassAddress(String classAddress) {
        this.address = classAddress;
    }
}
