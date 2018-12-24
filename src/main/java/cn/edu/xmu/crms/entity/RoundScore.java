package cn.edu.xmu.crms.entity;

import java.math.BigInteger;

/**
 * @ClassName RoundScore
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 3:11
 **/
public class RoundScore {
    private BigInteger roundID;
    private BigInteger teamID;
    private Double totalScore;
    private Double presentationScore;
    private Double questionScore;
    private Double reportScore;

    public BigInteger getRoundID() {
        return roundID;
    }

    public void setRoundID(BigInteger roundID) {
        this.roundID = roundID;
    }

    public BigInteger getTeamID() {
        return teamID;
    }

    public void setTeamID(BigInteger teamID) {
        this.teamID = teamID;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(Double presentationScore) {
        this.presentationScore = presentationScore;
    }

    public Double getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(Double questionScore) {
        this.questionScore = questionScore;
    }

    public Double getReportScore() {
        return reportScore;
    }

    public void setReportScore(Double reportScore) {
        this.reportScore = reportScore;
    }
}
