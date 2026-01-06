package br.com.feedsync.FeedSync.controller.gateway;

import br.com.feedsync.FeedSync.dto.NotificationRequest;
import br.com.feedsync.FeedSync.service.NotificationGatewayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationGatewayService service;

    public NotificationController(NotificationGatewayService service) {
        this.service = service;
    }

    @PostMapping
    public String send(@RequestBody NotificationRequest request) {

        if (request.getFeedbackId() == null || request.getFeedbackId().isBlank()) {
            return "feedbackId is null or empty";
        }

        return service.sendNotification(request.getFeedbackId());
    }

}
