package br.com.feedsync.functions.notification_service;

import br.com.feedsync.functions.notification_service.model.Feedback;
import br.com.feedsync.functions.notification_service.model.NotificationLog;
import br.com.feedsync.functions.notification_service.model.User;
import br.com.feedsync.functions.notification_service.model.enums.Profile;
import br.com.feedsync.functions.notification_service.repository.FeedbackRepository;
import br.com.feedsync.functions.notification_service.repository.NotificationLogRepository;
import br.com.feedsync.functions.notification_service.repository.UserRepository;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.util.*;
import java.util.stream.Collectors;

public class NotificationServiceApplication implements HttpFunction {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            response.setStatusCode(405);
            response.getWriter().write("Method not allowed");
            return;
        }

        Gson gson = new Gson();
        Map<String, Object> body = gson.fromJson(request.getReader(), Map.class);

        String feedbackId = body != null ? (String) body.get("feedbackId") : null;

        if (feedbackId == null || feedbackId.isEmpty()) {
            response.setStatusCode(400);
            response.getWriter().write("feedbackId é obrigatório.");
            return;
        }

        Firestore db = FirebaseConfig.getFirestore();

        UserRepository userRepository = new UserRepository(db);
        FeedbackRepository feedbackRepository = new FeedbackRepository(db);
        NotificationLogRepository notificationLogRepository = new NotificationLogRepository(db);

        var adminsFind = userRepository.findByProfile(String.valueOf(Profile.ADMIN));
        var feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback não encontrado: " + feedbackId));

        List<String> adminEmails = adminsFind.stream()
                .map(User::getEmail)
                .toList();

        NotificationLog notificationLog = new NotificationLog(
                UUID.randomUUID().toString(),
                new ArrayList<>(adminEmails),
                feedback,
                new Date()
        );

        var logSaved = notificationLogRepository.save(notificationLog);

        var admins = String.join(", ", adminEmails);

        var email = String.format(
                "\n====================================================\n" +
                        "              EMAIL SIMULADO (NOTIFICAÇÃO)\n" +
                        "====================================================\n" +
                        "Admins notificados: %s\n" +
                        "----------------------------------------------------\n" +
                        "Feedback ID: %s\n" +
                        "Rating: %d\n" +
                        "Urgente: %s\n" +
                        "Autor: %s (%s)\n" +
                        "Curso: %s\n" +
                        "Aula: %s\n" +
                        "Comentario: %s\n" +
                        "Data de envio: %s\n" +
                        "====================================================",
                admins,
                feedback.getFeedbackId(),
                feedback.getRating(),
                feedback.getUrgent() ? "SIM" : "NAO",
                feedback.getAuthor().getName(),
                feedback.getAuthor().getEmail(),
                feedback.getContext().getCourseName(),
                feedback.getContext().getLessonTitle(),
                feedback.getComment(),
                new Date()
        );

        log.info(email);

        BufferedWriter writer = response.getWriter();
        writer.write("Feeedback : " + feedbackId + " Enviado para os emails : " + admins);

    }
}
