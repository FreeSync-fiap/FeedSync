package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.client.NotificationClient;
import org.springframework.stereotype.Service;

@Service
public class NotificationGatewayService {

    private final NotificationClient client;

    public NotificationGatewayService(NotificationClient client) {
        this.client = client;
    }

    public String sendNotification (String feedbackId) {
        return client.sendNotification(feedbackId);
    }

}
