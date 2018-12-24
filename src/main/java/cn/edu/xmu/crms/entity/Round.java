package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName Round
 * @Description 讨论课轮次
 * @Author Hongqiwu
 * @Date 2018/12/17 15:05
 **/
public class Round {
    private BigInteger id;
    private BigInteger courseID;
    private Integer roundSerial;
    private Integer presentationScoreMethod;
    private Integer reportScoreMethod;
    private Integer questionScoreMethod;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger ID) {
        this.id = id;
    }

    public BigInteger getCourseID() {
        return courseID;
    }

    public void setCourseID(BigInteger courseID) {
        this.courseID = courseID;
    }

    public Integer getRoundSerial() {
        return roundSerial;
    }

    public void setRoundSerial(Integer roundSerial) {
        this.roundSerial = roundSerial;
    }

    public Integer getPresentationScoreMethod() {
        return presentationScoreMethod;
    }

    public void setPresentationScoreMethod(Integer presentationScoreMethod) {
        this.presentationScoreMethod = presentationScoreMethod;
    }

    public Integer getReportScoreMethod() {
        return reportScoreMethod;
    }

    public void setReportScoreMethod(Integer reportScoreMethod) {
        this.reportScoreMethod = reportScoreMethod;
    }

    public Integer getQuestionScoreMethod() {
        return questionScoreMethod;
    }

    public void setQuestionScoreMethod(Integer questionScoreMethod) {
        this.questionScoreMethod = questionScoreMethod;
    }
}
