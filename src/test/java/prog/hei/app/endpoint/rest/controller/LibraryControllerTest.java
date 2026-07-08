package prog.hei.app.endpoint.rest.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.entity.Library;
import prog.hei.app.exception.GlobalExceptionHandler;
import prog.hei.app.exception.LibraryNotFoundException;
import prog.hei.app.service.LibraryService;

@WebMvcTest({LibraryController.class, GlobalExceptionHandler.class})
public class LibraryControllerTest {
  @Autowired private MockMvc mockMvc;
  @MockBean private LibraryService libraryService;

  private Library behenjyLibrary;
  private Library ambatolampyLibrary;

  @BeforeEach
  void setUp() {
    behenjyLibrary =
        new Library(
            UUID.randomUUID(), "Behenjy Library", "Behenjy RN7", "0380000001", new ArrayList<>());

    ambatolampyLibrary =
        new Library(
            UUID.randomUUID(),
            "Ambatolampy Library",
            "Ambatolampy 104",
            "0380000002",
            new ArrayList<>());
  }

  @Test
  void getById_shouldReturn200_when_Library_exists() throws Exception {
    var existingUUID = UUID.randomUUID();

    var response =
        new LibraryResponse(
            behenjyLibrary.getId(),
            behenjyLibrary.getName(),
            behenjyLibrary.getAddress(),
            behenjyLibrary.getPhoneNumber(),
            new ArrayList<>());

    when(libraryService.getById(existingUUID)).thenReturn(response);

    mockMvc.perform(get("/libraries/" + existingUUID)).andExpect(status().isOk());
  }

  @Test
  void getById_shouldReturn404_when_library_doesNotExist() throws Exception {
    var randomUUID = UUID.randomUUID();

    when(libraryService.getById(randomUUID)).thenThrow(new LibraryNotFoundException(randomUUID));

    mockMvc.perform(get("/libraries/" + randomUUID)).andExpect(status().isNotFound());
  }

  @Test
  void getById_shouldReturn400_when_IdIsNotValid() throws Exception {
    mockMvc.perform(get("/libraries/123")).andExpect(status().isBadRequest());
  }
}
