package br.com.feedsync.FeedSync.entity;

public class ReportCourseAverage {

    private String courseId;
    private String courseName;
    private Double averageRating;

    public ReportCourseAverage() {}

    public ReportCourseAverage(String courseId, String courseName, Double averageRating) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.averageRating = averageRating;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}