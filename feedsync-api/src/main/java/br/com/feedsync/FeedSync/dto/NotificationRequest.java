package br.com.feedsync.FeedSync.dto;

public record NotificationRequest (
        String feedbackId
) {

    public String getFeedbackId() {
        return feedbackId;
    }
}
