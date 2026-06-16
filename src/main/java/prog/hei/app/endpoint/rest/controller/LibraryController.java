package prog.hei.app.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prog.hei.app.dto.library.request.LibraryRequest;
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

  @GetMapping("/libraries/{id}")
  public LibraryResponse getById(@PathVariable UUID id) {
    return libraryService.getById(id);
  }

  @PostMapping("/libraries")
  @ResponseStatus(HttpStatus.CREATED)
  public LibraryResponse create(@RequestBody @Valid LibraryRequest request) {
    return libraryService.create(request);
  }

  @PutMapping("/libraries/{id}")
  @ResponseStatus(HttpStatus.OK)
  public LibraryResponse update(@PathVariable UUID id, @RequestBody @Valid LibraryRequest request) {
    return libraryService.update(id, request);
  }

  @DeleteMapping("/libraries/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    libraryService.delete(id);
  }
}
