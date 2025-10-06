package com.newton.kurazetuapi.shared.exceptions;

import com.newton.kurazetuapi.shared.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex, WebRequest request) {
        log.warn("Custom exception: {} at {}", ex.getMessage(), request.getDescription(false));

        HttpStatus status = mapCustomExceptionToHttpStatus(ex.getMessage());
        return ResponseEntity.status(status)
                .body(new ApiResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        log.warn("Resource conflict: {} at {}", ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(ex.getMessage(), null));
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.warn("Access denied: {} at {}", ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse("Access denied. Insufficient permissions", null));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.warn("Invalid argument: {} at {}", ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(ex.getMessage(), null));
    }

    // Data Errors
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        log.error("Data integrity violation at {}", request.getDescription(false));

        String message = "Data integrity violation";
        if (ex.getMessage() != null &&
                (ex.getMessage().toLowerCase().contains("duplicate") ||
                        ex.getMessage().toLowerCase().contains("unique"))) {
            message = "The provided data already exists";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse(message, null));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse> handleSQLException(SQLException ex, WebRequest request) {
        log.error("Database error at {}", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Database error. Please try again later", null));
    }

    // HTTP Errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        log.warn("Malformed request at {}", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("Malformed request. Please check your request format", null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {
        log.warn("Method not supported: {} at {}", ex.getMethod(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ApiResponse("HTTP method '" + ex.getMethod() + "' is not supported", null));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex, WebRequest request) {
        log.warn("Unsupported media type at {}", request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(new ApiResponse("Unsupported media type. Use 'application/json'", null));
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MissingRequestHeaderException.class
    })
    public ResponseEntity<ApiResponse> handleMissingRequestDataException(Exception ex, WebRequest request) {
        log.warn("Missing request data at {}: {}", request.getDescription(false), ex.getMessage());

        String message;
        if (ex instanceof MissingServletRequestParameterException) {
            message = "Missing required parameter: " + ((MissingServletRequestParameterException) ex).getParameterName();
        } else {
            message = "Missing required header: " + ((MissingRequestHeaderException) ex).getHeaderName();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(message, null));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        log.warn("Endpoint not found: {} {} at {}", ex.getHttpMethod(), ex.getRequestURL(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Endpoint not found", null));
    }

    // Generic Error Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex, WebRequest request) {
        log.error("Unexpected error at {}: {}", request.getDescription(false), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("An unexpected error occurred. Please try again later", null));
    }

    private HttpStatus mapCustomExceptionToHttpStatus(String message) {
        if (message == null) return HttpStatus.INTERNAL_SERVER_ERROR;

        String lowerMessage = message.toLowerCase();

        if (lowerMessage.contains("not found") || lowerMessage.contains("does not exist")) {
            return HttpStatus.NOT_FOUND;
        }

        if (lowerMessage.contains("already exists") || lowerMessage.contains("duplicate")) {
            return HttpStatus.CONFLICT;
        }

        if (lowerMessage.contains("unauthorized") ||
                lowerMessage.contains("invalid credentials") ||
                lowerMessage.contains("authentication failed") ||
                lowerMessage.contains("invalid token") ||
                lowerMessage.contains("expired")) {
            return HttpStatus.UNAUTHORIZED;
        }

        if (lowerMessage.contains("forbidden") ||
                lowerMessage.contains("access denied") ||
                lowerMessage.contains("permission")) {
            return HttpStatus.FORBIDDEN;
        }

        if (lowerMessage.contains("disabled") ||
                lowerMessage.contains("not verified") ||
                lowerMessage.contains("invalid") ||
                lowerMessage.contains("required") ||
                lowerMessage.contains("verification failed")) {
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}