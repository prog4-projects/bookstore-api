package prog.hei.app.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.mapper.BookEditionMapper;
import prog.hei.app.repository.BookRepository;
import prog.hei.app.service.BookEditionService;

@RestController
@RequestMapping("/book-editions")
@RequiredArgsConstructor
public class BookEditionController {

  private final BookEditionService service;
  private final BookEditionMapper mapper;
  private final BookRepository bookRepository;

  @PostMapping
  public BookEditionResponse create(@RequestBody @Valid BookEditionRequest request) {

    Book book =
        bookRepository
            .findById(request.bookId())
            .orElseThrow(() -> new BookNotFoundException(request.bookId()));

    BookEdition entity = mapper.toEntity(request, book);

    return mapper.toResponse(service.create(entity));
  }

  @GetMapping
  public List<BookEditionResponse> getAll() {
    return service.findAll().stream().map(mapper::toResponse).toList();
  }

  @GetMapping("/{id}")
  public BookEditionResponse getById(@PathVariable UUID id) {
    return mapper.toResponse(service.findById(id));
  }

  @PutMapping("/{id}")
  public BookEditionResponse update(
      @PathVariable UUID id, @RequestBody @Valid BookEditionRequest request) {

    Book book =
        bookRepository
            .findById(request.bookId())
            .orElseThrow(() -> new BookNotFoundException(request.bookId()));

    BookEdition entity = mapper.toEntity(request, book);

    return mapper.toResponse(service.update(id, entity));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id) {
    service.delete(id);
  }
}
