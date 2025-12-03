package br.com.feedsync.FeedSync.controller.gateway;

import br.com.feedsync.FeedSync.service.NotificationGatewayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationGatewayService service;

    public NotificationController(NotificationGatewayService service) {
        this.service = service;
    }

    @GetMapping
    public String teste(@RequestParam(required = true) String feedbackId) {

        if (feedbackId == null || feedbackId.isBlank()) {
            return "feedbackId is null or empty";
        }

        return service.sendNotification(feedbackId);
    }

}
