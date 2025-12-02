package br.com.feedsync.FeedSync.controller.gateway;

import br.com.feedsync.FeedSync.service.NotificationGatewayService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationGatewayService service;

    public NotificationController(NotificationGatewayService service) {
        this.service = service;
    }

    @GetMapping
    public String teste () {
        return service.teste();
    }

}
