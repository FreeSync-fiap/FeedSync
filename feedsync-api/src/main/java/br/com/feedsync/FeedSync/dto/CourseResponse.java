package br.com.feedsync.FeedSync.dto;

import java.util.Date;
import java.util.List;

public record CourseResponse(
        String courseId,
        String courseName,
        String description,
        Date createdAt,
        List<LessonResponse> lessons
) {}