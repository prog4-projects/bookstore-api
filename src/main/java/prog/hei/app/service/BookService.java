package prog.hei.app.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.dto.book.response.BookStockResponse;
import prog.hei.app.entity.*;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.mapper.BookMapper;
import prog.hei.app.repository.*;

@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookEditionRepository bookEditionRepository;
  private final AuthorBookRepository authorBookRepository;
  private final BookMapper bookMapper;

  @Transactional(readOnly = true)
  public List<BookResponse> findAll() {
    return bookRepository.findAll().stream().map(bookMapper::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public BookResponse findById(UUID id) {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    return bookMapper.toResponse(book);
  }

  @Transactional
  public BookResponse create(BookRequest request) {
    Book book = bookRepository.save(bookMapper.toEntity(request));

    return bookMapper.toResponse(book);
  }

  @Transactional
  public BookResponse update(UUID id, BookRequest request) {

    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    book.setTitle(request.title());
    book.setDescription(request.description());
    book.setGender(request.gender());

    return bookMapper.toResponse(book);
  }

  @Transactional
  public void delete(UUID id) {

    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

    bookRepository.delete(book);
  }

  public BookStockResponse getStock(UUID id) {
    Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    Integer bookStock = bookRepository.getStockById(id);

    return new BookStockResponse(book.getId(), book.getTitle(), bookStock);
  }
}
