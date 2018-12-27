package cn.edu.xmu.crms.entity;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Round
 * @Description 讨论课轮次
 * @Author Hongqiwu
 * @Date 2018/12/17 15:05
 **/
public class Round {
    private BigInteger id;
    private Course course;
    private Integer roundSerial;
    private Integer presentationScoreMethod;
    private Integer reportScoreMethod;
    private Integer questionScoreMethod;
    private List<Map<String,Object>> signUpNumber;

    public List<Map<String, Object>> getSignUpNumber() {
        return signUpNumber;
    }

    public void setSignUpNumber(List<Map<String, Object>> signUpNumber) {
        this.signUpNumber = signUpNumber;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
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
