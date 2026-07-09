package prog.hei.app.endpoint.rest.controller;

import java.util.List;
import java.util.UUID;
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
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.dto.book.response.BookStockResponse;
import prog.hei.app.dto.revenue.response.RevenueByGenderResponse;
import prog.hei.app.service.BookService;
import prog.hei.app.service.RevenueService;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;
  private final RevenueService revenueService;

  @GetMapping
  public List<BookResponse> getAll() {
    return bookService.findAll();
  }

  @GetMapping("/{id}")
  public BookResponse getById(@PathVariable UUID id) {
    return bookService.findById(id);
  }

  @GetMapping("/{id}/stock")
  public BookStockResponse getStock(@PathVariable UUID id) {
    return bookService.getStock(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponse create(@RequestBody BookRequest dto) {
    return bookService.create(dto);
  }

  @PutMapping("/{id}")
  public BookResponse update(@PathVariable UUID id, @RequestBody BookRequest dto) {
    return bookService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    bookService.delete(id);
  }

  @GetMapping("/revenues")
  public List<RevenueByGenderResponse> getRevenueByGender() {
    return revenueService.getRevenueByGender();
  }
}
