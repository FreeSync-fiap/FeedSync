package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LessonRequest(
        @Schema(example = "Lesson title")
        @NotBlank
        String lessonTitle,

        @Schema(example = "Lesson concepts")
        @NotBlank
        String description
) {}