package br.com.feedsync.functions.notification_service;

import br.com.feedsync.functions.notification_service.model.User;
import br.com.feedsync.functions.notification_service.model.enums.Profile;
import br.com.feedsync.functions.notification_service.repository.UserRepository;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;

public class NotificationServiceApplication implements HttpFunction {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        String feedbackId = request.getFirstQueryParameter("feedbackId")
                .orElse(null);

        if (feedbackId == null) {
            response.getWriter().write("FeedbackId is required");
            return;
        }

        Firestore db = FirebaseConfig.getFirestore();

        UserRepository repository = new UserRepository(db);

        var admins = repository.findByProfile(String.valueOf(Profile.ADMIN));

        for (User user : admins) {
            log.info("Admin: " + user.getEmail());
        }

        log.info("Feeedback : " + feedbackId + " - IS URGENT");

        BufferedWriter writer = response.getWriter();
        writer.write("Feeedback : " + feedbackId + " IS URGENT");

    }
}
