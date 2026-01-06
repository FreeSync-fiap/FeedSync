package br.com.feedsync.FeedSync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;

public record ReportResponse(
        @Schema(description = "ID do relatório", example = "report_week_50_2025")
        String reportId,

        @Schema(description = "Data de geração")
        Date generatedAt,

        @Schema(description = "Início do período analisado")
        Date periodStartDate,

        @Schema(description = "Fim do período analisado")
        Date periodEndDate,

        @Schema(description = "Média geral de avaliações", example = "4.35")
        Double overallAverageRating,

        @Schema(description = "Total de feedbacks processados", example = "150")
        Integer totalFeedbacksProcessed,

        @Schema(description = "Lista de comentários recorrentes")
        List<RecurringCommentDTO> recurringComments,

        @Schema(description = "Média de avaliação por curso")
        List<CourseAverageDTO> averageByCourse
) {

    public record RecurringCommentDTO(String term, Integer occurrences) {}
    public record CourseAverageDTO(String courseId, String courseName, Double averageRating) {}
}