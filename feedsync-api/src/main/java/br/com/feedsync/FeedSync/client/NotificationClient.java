package br.com.feedsync.FeedSync.client;

import br.com.feedsync.FeedSync.config.FeignClientConfig;
import br.com.feedsync.FeedSync.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        url = "${clients.notification-service.url}",
        configuration = FeignClientConfig.class
)
public interface NotificationClient {

    @PostMapping("/")
    String sendNotification(@RequestBody NotificationRequest request);

}
