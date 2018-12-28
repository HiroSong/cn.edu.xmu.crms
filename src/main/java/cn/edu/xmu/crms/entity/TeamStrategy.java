package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/28 10:23
 **/
public class TeamStrategy {

    private BigInteger courseID;
    private Integer strategySerial;
    private String strategyName;
    private BigInteger strategyID;

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public Integer getStrategyNumber() {
        return strategySerial;
    }

    public void setStrategyNumber(Integer strategySerial) {
        this.strategySerial = strategySerial;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public BigInteger getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(BigInteger strategyID) {
        this.strategyID = strategyID;
    }
}
