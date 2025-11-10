package br.com.feedsync.FeedSync.controller.handler;
import br.com.feedsync.FeedSync.dto.error.ErrorResponse;
import br.com.feedsync.FeedSync.dto.error.ErrorResponseInternal;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExeceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExeceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseInternal> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = new ErrorResponseInternal(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod()
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseInternal> handleRuntimeException(
            Exception ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = new ErrorResponseInternal(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod()
        );

        log.error(ex.getMessage(), ex);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundExeception (
            ResourceNotFoundException e,
            HttpServletRequest request) {

        var status = HttpStatus.NOT_FOUND.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Resource Not Found : [{} {}] - {} (status: {})", method, path, e.getMessage(), status);

        return ResponseEntity.status(status).body(new ErrorResponse(Instant.now() , status ,e.getMessage() , method , path));
    }


}
