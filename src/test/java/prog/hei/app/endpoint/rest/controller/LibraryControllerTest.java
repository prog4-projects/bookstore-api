package prog.hei.app.endpoint.rest.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prog.hei.app.dto.library.request.LibraryRequest;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.entity.Library;
import prog.hei.app.exception.GlobalExceptionHandler;
import prog.hei.app.exception.LibraryNotFoundException;
import prog.hei.app.service.LibraryService;

@WebMvcTest({LibraryController.class, GlobalExceptionHandler.class})
public class LibraryControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private LibraryService libraryService;
  @Autowired private ObjectMapper objectMapper;

  private UUID id;
  private Library library;
  private LibraryResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    library = new Library(id, "Behenjy Library", "Behenjy RN7", "0380000001", new ArrayList<>());
    response =
        new LibraryResponse(id, "Behenjy Library", "Behenjy RN7", "0380000001", new ArrayList<>());
  }

  @Test
  void getAll_shouldReturn200() throws Exception {
    when(libraryService.getAll()).thenReturn(List.of(response));

    mockMvc.perform(get("/libraries")).andExpect(status().isOk());
  }

  @Test
  void getById_shouldReturn200_whenLibraryExists() throws Exception {
    when(libraryService.getById(id)).thenReturn(response);

    mockMvc.perform(get("/libraries/" + id)).andExpect(status().isOk());
  }

  @Test
  void getById_shouldReturn404_whenLibraryDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    when(libraryService.getById(randomId)).thenThrow(new LibraryNotFoundException(randomId));

    mockMvc.perform(get("/libraries/" + randomId)).andExpect(status().isNotFound());
  }

  @Test
  void getById_shouldReturn400_whenIdIsNotValid() throws Exception {
    mockMvc.perform(get("/libraries/123")).andExpect(status().isBadRequest());
  }

  @Test
  void create_shouldReturn201() throws Exception {
    var request = new LibraryRequest("New Library", "456 Street", "0380000099");
    when(libraryService.create(request)).thenReturn(response);

    mockMvc
        .perform(
            post("/libraries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void update_shouldReturn200_whenLibraryExists() throws Exception {
    var request = new LibraryRequest("Updated Lib", "789 Ave", "0380000088");
    var updatedResponse =
        new LibraryResponse(id, "Updated Lib", "789 Ave", "0380000088", new ArrayList<>());
    when(libraryService.update(id, request)).thenReturn(updatedResponse);

    mockMvc
        .perform(
            put("/libraries/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());
  }

  @Test
  void update_shouldReturn404_whenLibraryDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    var request = new LibraryRequest("Lib", "Addr", "0380000077");
    when(libraryService.update(randomId, request))
        .thenThrow(new LibraryNotFoundException(randomId));

    mockMvc
        .perform(
            put("/libraries/" + randomId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isNotFound());
  }

  @Test
  void delete_shouldReturn204_whenLibraryExists() throws Exception {
    mockMvc.perform(delete("/libraries/" + id)).andExpect(status().isNoContent());
  }

  @Test
  void delete_shouldReturn404_whenLibraryDoesNotExist() throws Exception {
    UUID randomId = UUID.randomUUID();
    doThrow(new LibraryNotFoundException(randomId)).when(libraryService).delete(randomId);

    mockMvc.perform(delete("/libraries/" + randomId)).andExpect(status().isNotFound());
  }
}
