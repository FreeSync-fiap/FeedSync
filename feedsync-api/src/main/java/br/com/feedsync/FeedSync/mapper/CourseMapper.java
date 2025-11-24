package br.com.feedsync.FeedSync.mapper;

import br.com.feedsync.FeedSync.dto.CourseRequest;
import br.com.feedsync.FeedSync.dto.CourseResponse;
import br.com.feedsync.FeedSync.dto.LessonRequest;
import br.com.feedsync.FeedSync.dto.LessonResponse;
import br.com.feedsync.FeedSync.entity.Course;
import br.com.feedsync.FeedSync.entity.Lesson;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEntity(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.courseName());
        course.setDescription(request.description());

        if (request.lessons() != null) {
            course.setLessons(request.lessons().stream()
                    .map(this::toLessonEntity)
                    .collect(Collectors.toList()));
        } else {
            course.setLessons(Collections.emptyList());
        }

        return course;
    }

    private Lesson toLessonEntity(LessonRequest request) {
        Lesson lesson = new Lesson();
        lesson.setLessonTitle(request.lessonTitle());
        lesson.setDescription(request.description());
        return lesson;
    }

    public CourseResponse toResponse(Course course) {
        List<LessonResponse> lessonResponses = course.getLessons() != null
                ? course.getLessons().stream().map(this::toLessonResponse).collect(Collectors.toList())
                : Collections.emptyList();

        return new CourseResponse(
                course.getCourseId(),
                course.getCourseName(),
                course.getDescription(),
                course.getCreatedAt(),
                lessonResponses
        );
    }

    private LessonResponse toLessonResponse(Lesson lesson) {
        return new LessonResponse(
                lesson.getLessonId(),
                lesson.getLessonTitle(),
                lesson.getDescription()
        );
    }

    public List<CourseResponse> toResponseList(List<Course> courses) {
        return courses.stream().map(this::toResponse).collect(Collectors.toList());
    }
}