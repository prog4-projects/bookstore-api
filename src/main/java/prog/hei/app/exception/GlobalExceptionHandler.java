package prog.hei.app.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prog.hei.app.dto.error.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerLibraryNotFound(
      NotFoundException notFoundException, HttpServletRequest request) {
    ErrorResponse response =
        new ErrorResponse(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            notFoundException.getMessage(),
            request.getRequestURI(),
            null);

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handlerInvalidRequestBody(
      MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();

    methodArgumentNotValidException
        .getBindingResult()
        .getFieldErrors()
        .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

    ErrorResponse response =
        new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "BAD_REQUEST",
            "Invalid request input",
            request.getRequestURI(),
            errors);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handlerInvalidJson(
      HttpMessageNotReadableException httpMessageNotReadableException, HttpServletRequest request) {
    ErrorResponse response =
        new ErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "INVALID_JSON",
            "Invalid JSON format or wrong data type",
            request.getRequestURI(),
            null);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
