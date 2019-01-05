package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName MemberLimitStrategy
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/29 1:57
 **/
public class MemberLimitStrategy {
    private BigInteger id;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
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

    private Integer minMember;
    private Integer maxMember;
}
