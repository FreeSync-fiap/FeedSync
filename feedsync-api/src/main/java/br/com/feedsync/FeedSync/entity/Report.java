package br.com.feedsync.FeedSync.entity;

import java.util.Date;
import java.util.List;

public class Report {

    private String reportId;
    private Date generatedAt;
    private Date periodStartDate;
    private Date periodEndDate;
    private Double overallAverageRating;
    private Integer totalFeedbacksProcessed;

    private List<ReportItem> recurringComments;
    private List<ReportCourseAverage> averageByCourse;

    public Report() {}

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Date getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodStartDate(Date periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    public Date getPeriodEndDate() {
        return periodEndDate;
    }

    public void setPeriodEndDate(Date periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public Double getOverallAverageRating() {
        return overallAverageRating;
    }

    public void setOverallAverageRating(Double overallAverageRating) {
        this.overallAverageRating = overallAverageRating;
    }

    public Integer getTotalFeedbacksProcessed() {
        return totalFeedbacksProcessed;
    }

    public void setTotalFeedbacksProcessed(Integer totalFeedbacksProcessed) {
        this.totalFeedbacksProcessed = totalFeedbacksProcessed;
    }

    public List<ReportItem> getRecurringComments() {
        return recurringComments;
    }

    public void setRecurringComments(List<ReportItem> recurringComments) {
        this.recurringComments = recurringComments;
    }

    public List<ReportCourseAverage> getAverageByCourse() {
        return averageByCourse;
    }

    public void setAverageByCourse(List<ReportCourseAverage> averageByCourse) {
        this.averageByCourse = averageByCourse;
    }
}