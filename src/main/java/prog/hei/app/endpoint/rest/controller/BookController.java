package prog.hei.app.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.service.BookService;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @GetMapping
  public List<BookDto> getAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public BookDto getById(@PathVariable String id) {
    return bookService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookDto create(@RequestBody BookDto dto) {
    return bookService.create(dto);
  }

  @PutMapping("/{id}")
  public BookDto update(@PathVariable String id, @RequestBody BookDto dto) {
    return bookService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable String id) {
    bookService.delete(id);
  }
}
