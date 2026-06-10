package prog.hei.app.endpoint.rest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.service.LibraryService;

@RestController
@RequiredArgsConstructor
public class LibraryController {

  private final LibraryService libraryService;

  @GetMapping("/libraries")
  @ResponseStatus(HttpStatus.OK)
  public List<LibraryResponse> getAll() {
    return libraryService.getAll();
  }
}
