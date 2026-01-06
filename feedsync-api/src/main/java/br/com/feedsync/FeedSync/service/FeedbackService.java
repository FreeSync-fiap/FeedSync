package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.dto.FeedbackResponse;
import br.com.feedsync.FeedSync.entity.*;
import br.com.feedsync.FeedSync.repository.FeedbackRepository;
import br.com.feedsync.FeedSync.repository.UserRepository;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;
    private final UserRepository userRepository;

    private final NotificationGatewayService notificationService;

    private final CourseService courseService;


    public FeedbackService(FeedbackRepository repository, UserRepository userRepository, NotificationGatewayService notificationService, CourseService courseService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.courseService = courseService;
    }

    public Feedback create(Feedback feedback , String username) {

        User userFind = userRepository.findByEmail(username);
        if (userFind == null) throw new ResourceNotFoundException("user" , "email", username);

        Author author = new Author(
                userFind.getUserId(),
                userFind.getName(),
                userFind.getEmail()
        );

        feedback.setAuthor(author);

        Course courseFind = courseService.findById(feedback.getContext().getCourseId());
        if (courseFind == null) throw new ResourceNotFoundException("course", "courseId", feedback.getContext().getCourseId());

        String lessonId = feedback.getContext().getLessonId();

        Lesson lessonFind = courseService.findLessonById(courseFind , lessonId);

        Context context = new Context();
        
        if (lessonFind != null) {
            context.setCourseId(courseFind.getCourseId());
            context.setCourseName(courseFind.getCourseName());
            context.setLessonId(lessonId);
            context.setLessonTitle(lessonFind.getLessonTitle());
        } else {
            context.setCourseId(courseFind.getCourseId());
            context.setCourseName(courseFind.getCourseName());
            context.setLessonId(null);
            context.setLessonTitle(null);
        }

        feedback.setContext(context);

        Date now = new  Date();

        feedback.setActive(true);
        feedback.setFeedbackId(UUID.randomUUID().toString());
        feedback.setCreatedAt(now);
        feedback.setUpdatedAt(now);

        Feedback saved = repository.save(feedback);

        if (saved.getUrgent()) {
            notificationService.sendNotification(saved.getFeedbackId());
        }

        return saved;

    }

    public List<Feedback> findAll(Boolean active) {
        if (active == null) return repository.findAll();

        return repository.findByField("active", active);
    }

    public Feedback findFeedbackById(String id) {
        return repository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("feedback", "feedbackId", id) );
    }

    public void deleteFeedbackById(String feedbackId) {
        repository.deleteById(feedbackId);
    }


}
