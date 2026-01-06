package br.com.feedsync.FeedSync.controller.handler;

import br.com.feedsync.FeedSync.dto.error.ErrorResponse;
import br.com.feedsync.FeedSync.dto.error.ErrorResponseInternal;
import br.com.feedsync.FeedSync.dto.error.ValidationErrorResponse;
import br.com.feedsync.FeedSync.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExeceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExeceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(), status, e.getMessage(), request.getRequestURI(), request.getMethod()
        ));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException e, HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(), status, "Endpoint não encontrado. Verifique a URL.", request.getRequestURI(), request.getMethod()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST.value();
        List<ValidationErrorResponse.FieldMessage> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err ->
                errors.add(new ValidationErrorResponse.FieldMessage(err.getField(), err.getDefaultMessage()))
        );

        return ResponseEntity.status(status).body(new ValidationErrorResponse(
                Instant.now(), status, "Erro de validação", request.getRequestURI(), request.getMethod(), errors
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonError(HttpMessageNotReadableException e, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(), status, "Corpo da requisição inválido", request.getRequestURI(), request.getMethod()
        ));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException e, HttpServletRequest request) {
        var status = HttpStatus.UNAUTHORIZED.value();
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(), status, "Falha de autenticação: Credenciais inválidas.", request.getRequestURI(), request.getMethod()
        ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e, HttpServletRequest request) {
        var status = HttpStatus.FORBIDDEN.value();
        return ResponseEntity.status(status).body(new ErrorResponse(
                Instant.now(), status, "Acesso negado: Você não tem permissão para realizar esta ação.", request.getRequestURI(), request.getMethod()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseInternal> handleGeneric(Exception e, HttpServletRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("Erro interno não tratado: {}", e.getMessage(), e);

        return ResponseEntity.status(status).body(new ErrorResponseInternal(
                Instant.now(), status.value(), status.getReasonPhrase(), e.getMessage(), request.getRequestURI(), request.getMethod()
        ));
    }
}