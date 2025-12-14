package br.com.feedsync.functions.notification_service.repository;

import br.com.feedsync.functions.notification_service.model.Feedback;
import com.google.cloud.firestore.Firestore;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class FeedbackRepository {

    private final Firestore db;

    public FeedbackRepository(Firestore db) {
        this.db = db;
    }

    public Optional<Feedback> findById(String feedbackId) {
        try {
            var snapshot = db.collection("feedbacks")
                    .document(feedbackId)
                    .get()
                    .get();

            if (!snapshot.exists()) {
                return Optional.empty();
            }

            return Optional.ofNullable(snapshot.toObject(Feedback.class));

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }


}
