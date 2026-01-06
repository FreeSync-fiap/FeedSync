package br.com.feedsync.functions.notification_service.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class NotificationLog {

    private String notificationLogId;
    private ArrayList<String> adminEmails;
    private Feedback feedback;
    private Date sendAt;

    public NotificationLog() {
    }

    public NotificationLog(String notificationLogId, ArrayList<String> adminEmails, Feedback feedback, Date sendAt) {
        this.notificationLogId = notificationLogId;
        this.adminEmails = adminEmails;
        this.feedback = feedback;
        this.sendAt = sendAt;
    }

    public String getNotificationLogId() {
        return notificationLogId;
    }

    public void setNotificationLogId(String notificationLogId) {
        this.notificationLogId = notificationLogId;
    }

    public ArrayList<String> getAdminEmails() {
        return adminEmails;
    }

    public void setAdminEmails(ArrayList<String> adminEmails) {
        this.adminEmails = adminEmails;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }
}
