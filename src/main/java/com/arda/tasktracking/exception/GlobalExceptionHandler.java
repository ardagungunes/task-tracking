package com.arda.tasktracking.exception;

import com.arda.tasktracking.response.ApiError;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // Wrong password, disabled account, etc.
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException exc){
        return build(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException exc){
        return build(HttpStatus.FORBIDDEN, "You do not have permission to do this");
    }

    // Safety net for anything not handled elsewhere
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception exc){
        log.error("Unexpected error", exc);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatusCode status,
                                                                            WebRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ApiError body = new ApiError(HttpStatus.BAD_REQUEST.value(), "Validation failed",
                fieldErrors, Instant.now());
        return ResponseEntity.badRequest().body(body);
    }

    // Spring's own errors and ResponseStatusException
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode status,
                                                             WebRequest request) {
        String message = (ex instanceof ErrorResponse errorResponse && errorResponse.getBody().getDetail() != null)
                ? errorResponse.getBody().getDetail()
                : "Request could not be processed";
        return ResponseEntity.status(status).headers(headers).body(new ApiError(status.value(), message));
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiError(status.value(), message));
    }
}
