package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.entity.Course;
import br.com.feedsync.FeedSync.entity.Lesson;
import br.com.feedsync.FeedSync.entity.User;
import br.com.feedsync.FeedSync.repository.CourseRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    public Course create(Course course) {
        course.setCourseId(UUID.randomUUID().toString());
        course.setCreatedAt(new Date());

        if (course.getLessons() != null) {
            for (Lesson lesson : course.getLessons()) {
                lesson.setLessonId(UUID.randomUUID().toString());
            }
        }

        return repository.save(course);
    }

    public Course findById(String courseId) {
        return repository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("course", "courseId", courseId));
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public void delete(String courseId) {
        repository.deleteById(courseId);
    }

    public Course update(String id, Course course) {
        var existing = findById(id);

        existing.setCourseName(course.getCourseName());
        existing.setDescription(course.getDescription());

        if (course.getLessons() != null) {
            course.getLessons().forEach(l -> l.setLessonId(UUID.randomUUID().toString()));
            existing.setLessons(course.getLessons());
        }

        existing.setUpdatedAt(new Date());
        return repository.save(existing);
    }
}