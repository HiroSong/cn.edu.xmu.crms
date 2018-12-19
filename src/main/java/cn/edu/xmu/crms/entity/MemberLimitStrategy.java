package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName MemberLimitStrategy
 * @Description 组队成员限制策略
 * @Author Hongqiwu
 * @Date 2018/12/17 15:53
 **/
public class MemberLimitStrategy {
    private BigInteger id;
    private Integer minMember;
    private Integer maxMember;

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
}
