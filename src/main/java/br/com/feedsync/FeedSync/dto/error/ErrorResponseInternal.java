package br.com.feedsync.FeedSync.dto.error;

import java.time.Instant;

public record ErrorResponseInternal(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path, String method
) {
}
