package br.com.feedsync.FeedSync.dto.error;

import java.time.Instant;
import java.util.List;

public record ValidationErrorResponse(
        Instant timestamp,
        int status,
        String message,
        String path,
        String method,
        List<FieldMessage> errors
) {
    public record FieldMessage(String field, String message) {}
}