package br.com.feedsync.FeedSync.client;

import br.com.feedsync.FeedSync.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "notification-service",
        url = "${clients.notification-service.url}",
        configuration = FeignClientConfig.class
)
public interface NotificationClient {

    @GetMapping("/")
    String sendNotification(@RequestParam("feedbackId") String feedbackId);

}
