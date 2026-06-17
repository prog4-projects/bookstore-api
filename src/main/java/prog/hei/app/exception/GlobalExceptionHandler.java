package prog.hei.app.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prog.hei.app.dto.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(
      NotFoundException exception, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND", exception.getMessage(), request);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleEntityNotFound(EntityNotFoundException exception) {
    return Map.of("error", exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidation(IllegalArgumentException exception) {
    return Map.of("error", exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleInvalidRequestBody(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    Map<String, String> fieldErrors = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError -> fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));
    return buildErrorResponse(
        HttpStatus.BAD_REQUEST, "BAD_REQUEST", "Invalid request input", request, fieldErrors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJson(
      HttpMessageNotReadableException exception, HttpServletRequest request) {
    return buildErrorResponse(
        HttpStatus.BAD_REQUEST, "INVALID_JSON", "Invalid JSON format or wrong data type", request);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      HttpStatus status, String error, String message, HttpServletRequest request) {
    return buildErrorResponse(status, error, message, request, null);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      HttpStatus status,
      String error,
      String message,
      HttpServletRequest request,
      Map<String, String> fieldErrors) {
    return ResponseEntity.status(status)
        .body(
            new ErrorResponse(
                Instant.now(),
                status.value(),
                error,
                message,
                request.getRequestURI(),
                fieldErrors));
  }
}
