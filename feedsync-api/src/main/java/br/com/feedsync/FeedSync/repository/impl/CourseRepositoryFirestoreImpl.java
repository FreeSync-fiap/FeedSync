package br.com.feedsync.FeedSync.repository.impl;

import br.com.feedsync.FeedSync.entity.Course;
import br.com.feedsync.FeedSync.repository.CourseRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class CourseRepositoryFirestoreImpl implements CourseRepository {

    private final Firestore db;

    public CourseRepositoryFirestoreImpl(Firestore db) {
        this.db = db;
    }

    @Override
    public Course save(Course course) {
        try {
            db.collection("courses")
                    .document(course.getCourseId())
                    .set(course)
                    .get();
            return course;
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Course> findById(String courseId) {
        try {
            var snapshot = db.collection("courses")
                    .document(courseId)
                    .get()
                    .get();

            if (!snapshot.exists()) {
                return Optional.empty();
            }

            return Optional.ofNullable(snapshot.toObject(Course.class));
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Course> findAll() {
        try {
            return db.collection("courses").get().get().toObjects(Course.class);
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(String courseId) {
        try {
            var docRef = db.collection("courses").document(courseId);
            var snapshot = docRef.get().get();

            if (!snapshot.exists()) {
                throw new ResourceNotFoundException("course", "courseId", courseId);
            }

            docRef.delete().get();
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}