package br.com.feedsync.FeedSync.repository;

import br.com.feedsync.FeedSync.entity.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findById(String courseId);
    List<Course> findAll();
    void deleteById(String courseId);
}