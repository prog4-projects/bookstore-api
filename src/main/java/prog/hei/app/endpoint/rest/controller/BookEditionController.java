package prog.hei.app.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.service.BookEditionService;

@RestController
@RequestMapping("/book-editions")
@RequiredArgsConstructor
public class BookEditionController {

  private final BookEditionService service;

  @PostMapping
  public BookEditionResponse create(@RequestBody @Valid BookEditionRequest request) {
    return service.create(request);
  }

  @GetMapping
  public List<BookEditionResponse> getAll() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public BookEditionResponse getById(@PathVariable UUID id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public BookEditionResponse update(
          @PathVariable UUID id,
          @RequestBody @Valid BookEditionRequest request) {

    return service.update(id, request);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}