package br.com.feedsync.FeedSync.dto;

import br.com.feedsync.FeedSync.entity.Author;
import br.com.feedsync.FeedSync.entity.Context;

import java.util.Date;

public record FeedbackResponse(

        String feedbackId,
        Integer rating,

        String comment,
        Context context,

        Boolean isUrgent,

        Author author,

        Date createdAt,
        Date updatedAt,

        Boolean active

) {
}
