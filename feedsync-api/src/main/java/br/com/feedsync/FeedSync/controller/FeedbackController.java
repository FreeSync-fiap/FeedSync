package br.com.feedsync.FeedSync.controller;

import br.com.feedsync.FeedSync.controller.docs.FeedbackControllerDoc;
import br.com.feedsync.FeedSync.dto.FeedbackRequest;
import br.com.feedsync.FeedSync.dto.FeedbackResponse;
import br.com.feedsync.FeedSync.entity.Feedback;
import br.com.feedsync.FeedSync.mapper.FeedbackMapper;
import br.com.feedsync.FeedSync.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController implements FeedbackControllerDoc {

    private FeedbackService service;
    private FeedbackMapper mapper;

    public FeedbackController(FeedbackService service , FeedbackMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> create(
            @Validated @RequestBody FeedbackRequest feedback,
            @AuthenticationPrincipal UserDetails user
    ) {
        Feedback saved = service.create(
                mapper.toEntity(feedback) , user.getUsername()
        );

        return ResponseEntity.ok(mapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> list(
            @RequestParam(required = false) Boolean active
    ) {
        List<Feedback> feedbacks = service.findAll(active);
        return ResponseEntity.ok(mapper.toResponseList(feedbacks)) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> findFeedbackById(
            @PathVariable String id
    ) {
        var result = service.findFeedbackById(id);
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedbackById(
            @PathVariable String id
    ) {
        service.deleteFeedbackById(id);
        return ResponseEntity.ok().build();
    }


}
