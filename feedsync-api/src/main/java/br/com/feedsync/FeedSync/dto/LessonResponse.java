package br.com.feedsync.FeedSync.dto;

public record LessonResponse(
        String lessonId,
        String lessonTitle,
        String description
) {}