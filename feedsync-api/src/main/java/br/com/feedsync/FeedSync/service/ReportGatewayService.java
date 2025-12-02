package br.com.feedsync.FeedSync.service;

import br.com.feedsync.FeedSync.client.NotificationClient;
import br.com.feedsync.FeedSync.client.ReportClient;
import org.springframework.stereotype.Service;

@Service
public class ReportGatewayService {

    private final ReportClient client;

    ReportGatewayService (ReportClient client) {
        this.client = client;
    }

    public String teste () {
        return client.report();
    }

}
