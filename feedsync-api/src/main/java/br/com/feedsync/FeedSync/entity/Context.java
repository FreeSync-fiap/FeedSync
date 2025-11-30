package br.com.feedsync.FeedSync.entity;

public class Context {

    private String courseId;

    private String courseName;

    private String lessonId;

    private String lessonTitle;

    public Context() {
    }

    public Context(String courseId, String courseName, String lessonId, String lessonTitle) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
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

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }
}
