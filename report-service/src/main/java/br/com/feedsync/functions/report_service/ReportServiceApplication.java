package br.com.feedsync.functions.report_service;

import br.com.feedsync.functions.report_service.model.*;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ReportServiceApplication implements HttpFunction {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceApplication.class);
    private static final Gson gson = new Gson();

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        Firestore db = FirebaseConfig.getFirestore();
        log.info("Iniciando geração de relatório semanal Serverless Function...");

        List<Feedback> feedbacks = new ArrayList<>();
        var querySnapshot = db.collection("feedbacks").get().get();

        for (QueryDocumentSnapshot document : querySnapshot) {
            feedbacks.add(document.toObject(Feedback.class));
        }

        if (feedbacks.isEmpty()) {
            response.setStatusCode(HttpURLConnection.HTTP_NO_CONTENT);
            response.getWriter().write("Nenhum feedback encontrado para processar.");
            return;
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
                .limit(3)
                .map(e -> new ReportItem(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());

        Report report = new Report();
        report.setReportId("report_weekly_" + LocalDate.now().toString());
        report.setGeneratedAt(new Date());
        report.setPeriodStartDate(getDateDaysAgo(7));
        report.setPeriodEndDate(new Date());
        report.setTotalFeedbacksProcessed(feedbacks.size());
        report.setOverallAverageRating(overallAverage);
        report.setAverageByCourse(courseAverages);
        report.setRecurringComments(topComments);

        db.collection("reports").document(report.getReportId()).set(report).get();

        log.info("Relatório gerado e salvo: " + report.getReportId());

        response.setStatusCode(HttpURLConnection.HTTP_OK);
        response.setContentType("application/json");
        BufferedWriter writer = response.getWriter();
        writer.write(gson.toJson(report));
    }

    private Date getDateDaysAgo(int days) {
        return Date.from(LocalDate.now().minusDays(days)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}