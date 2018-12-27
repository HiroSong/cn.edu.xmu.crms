package cn.edu.xmu.crms.entity;

import java.math.BigInteger;
/**
 * @ClassName Attendance
 * @Author Hongqiwu
 * @Description 讨论课参加
 **/
public class Attendance {
    private BigInteger id;
    private BigInteger klassSeminarID;
    private Team team;
    private Integer teamOrder;
    private Integer bePresent;
    private String reportName;
    private String reportUrl;
    private String pptName;
    private String pptUrl;

    public BigInteger getID() {
        return id;
    }

    public void setID(BigInteger id) {
        this.id = id;
    }

    public BigInteger getKlassSeminarID() {
        return klassSeminarID;
    }

    public void setKlassSeminarID(BigInteger klassSeminarID) {
        this.klassSeminarID = klassSeminarID;
    }

    public void setTeam(Team team){this.team=team;}

    public Team getTeam(){return this.team;}

    public BigInteger getTeamID() { return team.getID(); }

    public void setTeamID(BigInteger teamID) { this.team.setID(teamID); }

    public Integer getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(Integer teamOrder) {
        this.teamOrder = teamOrder;
    }

    public Integer getBePresent() {
        return bePresent;
    }

    public void setBePresent(Integer bePresent) {
        this.bePresent = bePresent;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getPptName() {
        return pptName;
    }

    public void setPptName(String pptName) {
        this.pptName = pptName;
    }

    public String getPptUrl() {
        return pptUrl;
    }

    public void setPptUrl(String pptUrl) {
        this.pptUrl = pptUrl;
    }
}
