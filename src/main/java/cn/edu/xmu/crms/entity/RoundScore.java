package cn.edu.xmu.crms.entity;


/**
 * @ClassName RoundScore
 * @Description TODO
 * @Author Hongqiwu
 * @Date 2018/12/20 3:11
 **/
public class RoundScore {
    private Double totalScore;
    private Double presentationScore;
    private Double questionScore;
    private Double reportScore;
    private Round round;
    private Team team;

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
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
