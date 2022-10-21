package io.regent.blogrestapi.interceptor.handler;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.regent.blogrestapi.interceptor.exception.BlogAPIException;
import io.regent.blogrestapi.interceptor.exception.ResourceNotFoundException;
import io.regent.blogrestapi.interceptor.payload.ErrorDetails;

/**
 * Created by @author Ifeanyichukwu Otiwa
 * 19/10/2022
 */

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int INDEX_OF_URI_START = 4;
    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(final ResourceNotFoundException e,
                                                                        final WebRequest request) {
        final ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)))
                .status(e.getStatus())
                .errorMessage(String.format("%s not found with %s: %s", e.getResourceName(), e.getFieldName(), e.getFieldValue()))
                .path(request.getDescription(false).substring(INDEX_OF_URI_START))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogApiException(final BlogAPIException e,
                                                               final WebRequest request) {
        final ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)))
                .status(e.getStatus())
                .errorMessage(e.getMessage())
                .path(request.getDescription(false).substring(INDEX_OF_URI_START))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(final AccessDeniedException e,
                                                                    final WebRequest request) {
        final ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)))
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(e.getMessage())
                .path(request.getDescription(false).substring(INDEX_OF_URI_START))
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(final Exception e,
                                                              final WebRequest request) {
        final ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(e.getMessage())
                .path(request.getDescription(false).substring(INDEX_OF_URI_START))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  @NonNull final HttpHeaders headers,
                                                                  @NonNull final HttpStatus status,
                                                                  final WebRequest request) {
        final Map<String, String> errorMap = new HashMap<>();
        ex.getAllErrors().forEach(error -> {
            final String fieldName = ((FieldError) error).getField();
            final String defaultMessage = error.getDefaultMessage();
            errorMap.put(fieldName, defaultMessage);
        });

        final ErrorResp response = new ErrorResp(
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)),
                errorMap,
                request.getDescription(false)
                        .substring(INDEX_OF_URI_START)
        );

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }


    private record ErrorResp(String timestamp, Map<String, String> errors, String path) {
    }
}
