package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.Feedback;
import br.com.feedsync.FeedSync.entity.Report;
import br.com.feedsync.FeedSync.entity.ReportCourseAverage;
import br.com.feedsync.FeedSync.entity.ReportItem;
import br.com.feedsync.FeedSync.repository.FeedbackRepository;
import br.com.feedsync.FeedSync.repository.ReportRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final FeedbackRepository feedbackRepository;

    public ReportService(ReportRepository reportRepository, FeedbackRepository feedbackRepository) {
        this.reportRepository = reportRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("report", "id", id));
    }

    public Report generateReport() {
        List<Feedback> feedbacks = feedbackRepository.findAll();

        if (feedbacks.isEmpty()) {
            throw new RuntimeException("Não há feedbacks para gerar relatório.");
        }

        double overallAverage = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        Map<String, List<Feedback>> feedbacksByCourse = feedbacks.stream()
                .filter(f -> f.getContext() != null && f.getContext().getCourseId() != null)
                .collect(Collectors.groupingBy(f -> f.getContext().getCourseId()));

        List<ReportCourseAverage> courseAverages = new ArrayList<>();

        for (var entry : feedbacksByCourse.entrySet()) {
            String courseId = entry.getKey();
            List<Feedback> courseFeedbacks = entry.getValue();

            double avg = courseFeedbacks.stream()
                    .mapToInt(Feedback::getRating)
                    .average()
                    .orElse(0.0);

            String courseName = courseFeedbacks.get(0).getContext().getCourseName();

            courseAverages.add(new ReportCourseAverage(courseId, courseName, avg));
        }

        Map<String, Long> wordCounts = feedbacks.stream()
                .map(Feedback::getComment)
                .filter(Objects::nonNull)
                .flatMap(c -> Arrays.stream(c.toLowerCase().split("\\s+")))
                .filter(w -> w.length() > 3)
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        List<ReportItem> topComments = wordCounts.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(3) // pega top 3
                .map(e -> new ReportItem(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());

        Report report = new Report();
        String reportId = "report_" + UUID.randomUUID().toString().substring(0, 8);
        report.setReportId(reportId);
        report.setGeneratedAt(new Date());
        report.setPeriodStartDate(getDateDaysAgo(30)); // Exemplo: últimos 30 dias
        report.setPeriodEndDate(new Date());
        report.setTotalFeedbacksProcessed(feedbacks.size());
        report.setOverallAverageRating(overallAverage);
        report.setAverageByCourse(courseAverages);
        report.setRecurringComments(topComments);

        return reportRepository.save(report);
    }

    private Date getDateDaysAgo(int days) {
        return Date.from(LocalDate.now().minusDays(days)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}