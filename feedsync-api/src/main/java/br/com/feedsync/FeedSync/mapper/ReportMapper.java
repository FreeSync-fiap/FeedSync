package br.com.feedsync.FeedSync.mapper;

import br.com.feedsync.FeedSync.dto.ReportResponse;
import br.com.feedsync.FeedSync.entity.Report;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    public ReportResponse toResponse(Report report) {
        var comments = report.getRecurringComments() != null ?
                report.getRecurringComments().stream()
                        .map(c -> new ReportResponse.RecurringCommentDTO(c.getTerm(), c.getOccurrences()))
                        .collect(Collectors.toList()) : Collections.<ReportResponse.RecurringCommentDTO>emptyList();

        var courses = report.getAverageByCourse() != null ?
                report.getAverageByCourse().stream()
                        .map(c -> new ReportResponse.CourseAverageDTO(c.getCourseId(), c.getCourseName(), c.getAverageRating()))
                        .collect(Collectors.toList()) : Collections.<ReportResponse.CourseAverageDTO>emptyList();

        return new ReportResponse(
                report.getReportId(),
                report.getGeneratedAt(),
                report.getPeriodStartDate(),
                report.getPeriodEndDate(),
                report.getOverallAverageRating(),
                report.getTotalFeedbacksProcessed(),
                comments,
                courses
        );
    }

    public List<ReportResponse> toResponseList(List<Report> reports) {
        if (reports == null) return Collections.emptyList();
        return reports.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}