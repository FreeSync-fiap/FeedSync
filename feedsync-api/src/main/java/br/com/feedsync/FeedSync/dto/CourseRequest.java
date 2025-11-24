package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CourseRequest(
        @Schema(example = "Course name.")
        @NotBlank
        String courseName,

        @Schema(example = "Course description.")
        @NotBlank
        String description,

        List<LessonRequest> lessons
) {}