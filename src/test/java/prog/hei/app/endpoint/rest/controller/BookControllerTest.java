package prog.hei.app.endpoint.rest.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.dto.book.response.BookStockResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.exception.GlobalExceptionHandler;
import prog.hei.app.service.BookService;

@WebMvcTest({BookController.class, GlobalExceptionHandler.class})
public class BookControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private BookService bookService;
  @Autowired private ObjectMapper objectMapper;

  private Book book;
  private BookResponse response;

  private UUID id;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    book = new Book();
    book.setId(id);
    book.setTitle("Test Title");
    book.setDescription("Description");
    book.setGender(BookGenderEnum.MYSTERY);
    response =
        new BookResponse(id, "Test Title", "Description", BookGenderEnum.MYSTERY, null, null);
  }

  @Test
  void getById_shouldReturn200_whenBookExists() throws Exception {
    when(bookService.findById(id)).thenReturn(response);

    mockMvc.perform(get("/books/" + id)).andExpect(status().isOk());
  }

  @Test
  void getById_shouldReturn404_whenBookDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    when(bookService.findById(randomId)).thenThrow(new BookNotFoundException(randomId));

    mockMvc.perform(get("/books/" + randomId)).andExpect(status().isNotFound());
  }

  @Test
  void getById_shouldReturn400_whenIdIsNotValid() throws Exception {
    mockMvc.perform(get("/books/123")).andExpect(status().isBadRequest());
  }

  @Test
  void getAll_shouldReturn200() throws Exception {
    when(bookService.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/books")).andExpect(status().isOk());
  }

  @Test
  void create_shouldReturn201() throws Exception {
    var request = new BookRequest("Test Title", "Description", BookGenderEnum.HORROR);
    when(bookService.create(request)).thenReturn(response);
    mockMvc
        .perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void update_shouldReturn200_whenBookExists() throws Exception {
    var request = new BookRequest("New Title", "New Desc", BookGenderEnum.FANTASY);
    var updatedResponse =
        new BookResponse(id, "New Title", "New Desc", BookGenderEnum.FANTASY, null, null);
    when(bookService.update(id, request)).thenReturn(updatedResponse);

    mockMvc
        .perform(
            put("/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void update_shouldReturn404_whenBookDoesNotExist() throws Exception {
    var randomId = UUID.randomUUID();
    var request = new BookRequest("Test Title", "Desc", BookGenderEnum.HISTORY);
    when(bookService.update(randomId, request)).thenThrow(new BookNotFoundException(randomId));

    mockMvc
        .perform(
            put("/books/" + randomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete_shouldReturn204_whenBookExists() throws Exception {
    mockMvc.perform(delete("/books/" + id)).andExpect(status().isNoContent());
  }

  @Test
  void delete_shouldReturn404_whenBookDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    doThrow(new BookNotFoundException(randomId)).when(bookService).delete(randomId);
    mockMvc.perform(delete("/books/" + randomId)).andExpect(status().isNotFound());
  }

  @Test
  void getStock_shouldReturn200_whenBookExists() throws Exception {
    var stockResponse = new BookStockResponse(id, "Test Title", 10);
    when(bookService.getStock(id)).thenReturn(stockResponse);

    mockMvc.perform(get("/books/" + id + "/stock")).andExpect(status().isOk());
  }

  @Test
  void getStock_shouldReturn404_whenBookDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    when(bookService.getStock(randomId)).thenThrow(new BookNotFoundException(randomId));

    mockMvc.perform(get("/books/" + randomId + "/stock")).andExpect(status().isNotFound());
  }
}
