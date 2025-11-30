package br.com.feedsync.FeedSync.repository.impl;

import br.com.feedsync.FeedSync.entity.Feedback;
import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.repository.FeedbackRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class FeedbackRepositoryFirestoreImpl implements FeedbackRepository {

    private final Firestore db;

    public FeedbackRepositoryFirestoreImpl(Firestore db) {
        this.db = db;
    }

    @Override
    public Feedback save(Feedback feedback) {
        try {
            db.collection("feedbacks")
                    .document(feedback.getFeedbackId().toString())
                    .set(feedback)
                    .get();
            return feedback;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Erro ao salvar feedbACK no Firestore", e);
        }
    }

    @Override
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
            throw new ResourceNotFoundException("feedback","feedbackId",feedbackId);
        }
    }

    @Override
    public List<Feedback> findAll() {
        try {
            return db.collection("feedbacks").get().get().toObjects(Feedback.class);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String feedbackId) {
        try {
            var docRef = db.collection("feedbacks").document(feedbackId);
            var snapshot = docRef.get().get();

            if (!snapshot.exists()) {
                throw new ResourceNotFoundException("feedback","feedbackId",feedbackId);
            }

            docRef.update("active", false).get();

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to delete feedback by ID", e);
        }

    }

    @Override
    public List<Feedback> findByField(String fieldName, Object value) {
        try {
            var querySnapshot = db.collection("feedbacks")
                    .whereEqualTo(fieldName, value)
                    .get()
                    .get();

            return querySnapshot.toObjects(Feedback.class);

        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error to find feedbacks by field: " + fieldName, e);
        }
    }

}
