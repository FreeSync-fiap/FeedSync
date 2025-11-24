package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LessonRequest(
        @Schema(example = "Introduction to Cloud Computing")
        @NotBlank
        String lessonTitle,

        @Schema(example = "Basic concepts about cloud...")
        @NotBlank
        String description
) {}