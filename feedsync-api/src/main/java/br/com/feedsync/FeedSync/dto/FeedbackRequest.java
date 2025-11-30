package br.com.feedsync.FeedSync.dto;

public record FeedbackRequest (

        Integer rating,
        String comment,
        ContextRequest context,
        Boolean isUrgent

) {
}
