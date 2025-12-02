package br.com.feedsync.FeedSync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.feedsync.FeedSync.client")
public class FeedSyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedSyncApplication.class, args);
	}

}
