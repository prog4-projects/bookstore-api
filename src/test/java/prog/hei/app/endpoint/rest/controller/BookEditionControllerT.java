package prog.hei.app.endpoint.rest.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.dto.bookEdition.response.BookEditionStockResponse;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;
import prog.hei.app.service.BookEditionService;

@WebMvcTest(BookEditionController.class)
class BookEditionControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private BookEditionService service;

  @Autowired private ObjectMapper objectMapper;

  private BookEditionRequest buildRequest() {
    return new BookEditionRequest(
        "123456789",
        BookLanguageEnum.ENGLISH,
        BookFormatEnum.PAPERBACK,
        200,
        "O'Reilly",
        new BigDecimal("20.5"),
        LocalDateTime.now(),
        UUID.randomUUID());
  }

  private BookEditionResponse buildResponse(UUID id) {
    return new BookEditionResponse(
        id,
        "123456789",
        BookLanguageEnum.ENGLISH,
        BookFormatEnum.PAPERBACK,
        200,
        "O'Reilly",
        new BigDecimal("20.5"),
        LocalDateTime.now(),
        UUID.randomUUID(),
        "Clean Code");
  }

  @Test
  void should_create_book_edition() throws Exception {
    UUID id = UUID.randomUUID();

    when(service.create(org.mockito.Mockito.any())).thenReturn(buildResponse(id));

    mockMvc
        .perform(
            post("/book-editions")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(buildRequest())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void should_get_all_book_editions() throws Exception {
    when(service.findAll()).thenReturn(List.of(buildResponse(UUID.randomUUID())));

    mockMvc
        .perform(get("/book-editions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }

  @Test
  void should_get_book_edition_by_id() throws Exception {
    UUID id = UUID.randomUUID();

    when(service.findById(id)).thenReturn(buildResponse(id));

    mockMvc
        .perform(get("/book-editions/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void should_update_book_edition() throws Exception {
    UUID id = UUID.randomUUID();

    when(service.update(org.mockito.Mockito.eq(id), org.mockito.Mockito.any()))
        .thenReturn(buildResponse(id));

    mockMvc
        .perform(
            put("/book-editions/" + id)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(buildRequest())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void should_delete_book_edition() throws Exception {
    UUID id = UUID.randomUUID();

    doNothing().when(service).delete(id);

    mockMvc.perform(delete("/book-editions/" + id)).andExpect(status().isOk());
  }

  @Test
  void should_Return_Low_Stock_BookEditions() throws Exception {
    UUID id = UUID.randomUUID();

    BookEditionStockResponse response = new BookEditionStockResponse(id, "Clean Code", 2);

    when(service.getLowStock(3)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/book-editions/low-stock"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].bookEditionId").value(id.toString()))
        .andExpect(jsonPath("$[0].title").value("Clean Code"))
        .andExpect(jsonPath("$[0].stock").value(2));
  }
}
