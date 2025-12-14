package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "ContextRequest",
        description = "Contexto relacionado ao feedback"
)
public record ContextRequest(

        @NotBlank(message = "courseId is mandatory")
        @Schema(
                description = "Identificador do curso relacionado ao feedback",
                example = "course-123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String courseId,

        @Schema(
                description = "Identificador da lição relacionada ao feedback",
                example = "lesson-456"
        )
        String lessonId

) {
}
