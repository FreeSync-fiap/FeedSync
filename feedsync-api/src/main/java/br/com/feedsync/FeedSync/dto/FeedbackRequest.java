package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "FeedbackRequest",
        description = "Objeto de requisição para criação de feedback"
)
public record FeedbackRequest (

        @NotNull(message = "Rating is mandatory")
        @Min(value = 0, message = "Rating must be at least 0")
        @Max(value = 10, message = "Rating must be at most 10")
        @Schema(
                description = "Nota do feedback",
                example = "8",
                minimum = "0",
                maximum = "10",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Integer rating,

        @NotNull(message = "Comment is mandatory")
        @Schema(
                description = "Comentário adicional do feedback",
                example = "A plataforma é muito intuitiva e rápida"
        )
        String comment,

        @Valid
        @Schema(
                description = "Contexto no qual o feedback foi gerado"
        )
        ContextRequest context,

        @NotNull(message = "isUrgent is mandatory")
        @Schema(
                description = "Indica se o feedback é urgente",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Boolean isUrgent

) {
}

