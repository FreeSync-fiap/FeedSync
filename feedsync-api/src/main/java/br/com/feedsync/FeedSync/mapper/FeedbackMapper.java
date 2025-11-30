package br.com.feedsync.FeedSync.mapper;


import br.com.feedsync.FeedSync.dto.ContextRequest;
import br.com.feedsync.FeedSync.dto.FeedbackRequest;
import br.com.feedsync.FeedSync.dto.FeedbackResponse;
import br.com.feedsync.FeedSync.entity.Context;
import br.com.feedsync.FeedSync.entity.Feedback;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {

    public Feedback toEntity(FeedbackRequest request) {
        Feedback entity = new Feedback();

        entity.setRating(request.rating());
        entity.setComment(request.comment());
        entity.setUrgent(request.isUrgent());

        entity.setContext(this.toEntity(request.context()));

        return entity;

    }

    public Context toEntity(ContextRequest request) {
        Context entity = new Context();

        entity.setCourseId(request.courseId());
        entity.setLessonId(request.lessonId());

        return entity;
    }

    public FeedbackResponse toResponse(Feedback feedback) {

        return new FeedbackResponse(
                feedback.getFeedbackId(),
                feedback.getRating(),
                feedback.getComment(),
                feedback.getContext(),
                feedback.getUrgent(),
                feedback.getAuthor(),
                feedback.getCreatedAt(),
                feedback.getUpdatedAt(),
                feedback.getActive()
        );


    }

    public List<FeedbackResponse> toResponseList(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
