package br.com.feedsync.FeedSync.repository;

import br.com.feedsync.FeedSync.entity.Feedback;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public interface FeedbackRepository {

    Feedback save(Feedback feedback);

    Optional<Feedback> findById(String feedbackId);

    List<Feedback> findAll();

    void deleteById(String feedbackId);

    List<Feedback> findByField(String field , Object value);
}
