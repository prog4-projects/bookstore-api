package prog.hei.app.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import prog.hei.app.dto.error.ErrorResponse;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler;
  private HttpServletRequest request;

  @BeforeEach
  void setUp() {
    handler = new GlobalExceptionHandler();
    request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn("/test");
  }

  @Test
  void handleNotFound_shouldReturn404() {
    NotFoundException ex = new NotFoundException("not found");

    ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody().error()).isEqualTo("NOT_FOUND");
    assertThat(response.getBody().message()).isEqualTo("not found");
    assertThat(response.getBody().path()).isEqualTo("/test");
  }

  @Test
  void handleValidation_shouldReturn400() {
    IllegalArgumentException ex = new IllegalArgumentException("bad arg");

    ResponseEntity<ErrorResponse> response = handler.handleValidation(ex, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().error()).isEqualTo("BAD_REQUEST");
    assertThat(response.getBody().message()).isEqualTo("bad arg");
  }

  @Test
  void handleTypeMismatch_shouldReturn400() {
    MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
    when(ex.getMessage()).thenReturn("Type mismatch");

    ResponseEntity<ErrorResponse> response = handler.handleTypeMismatch(ex, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().error()).isEqualTo("TYPE_MISMATCH");
  }

  @Test
  void handleInvalidRequestBody_shouldReturn400_withFieldErrors() {
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("obj", "title", "Title is required");
    when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError));

    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    when(ex.getBindingResult()).thenReturn(bindingResult);

    ResponseEntity<ErrorResponse> response = handler.handleInvalidRequestBody(ex, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().error()).isEqualTo("BAD_REQUEST");
    assertThat(response.getBody().fieldErrors()).containsEntry("title", "Title is required");
  }

  @Test
  void handleInvalidJson_shouldReturn400() {
    org.springframework.http.converter.HttpMessageNotReadableException ex =
        mock(org.springframework.http.converter.HttpMessageNotReadableException.class);

    ResponseEntity<ErrorResponse> response = handler.handleInvalidJson(ex, request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().error()).isEqualTo("INVALID_JSON");
  }
}
