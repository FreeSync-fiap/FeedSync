package br.com.feedsync.FeedSync.client;

import br.com.feedsync.FeedSync.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "report-service",
        url = "${clients.report-service.url}",
        configuration = FeignClientConfig.class
)
public interface ReportClient {

    @GetMapping("/")
    String report();

}
