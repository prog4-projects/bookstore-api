package prog.hei.app.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.exception.BookEditionNotFoundException;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.mapper.BookEditionMapper;
import prog.hei.app.repository.BookEditionRepository;
import prog.hei.app.repository.BookRepository;
import prog.hei.app.repository.StockMovementRepository;

@Service
@RequiredArgsConstructor
public class BookEditionService {

  private final BookEditionRepository repository;
  private final BookRepository bookRepository;
  private final BookEditionMapper mapper;
  private final StockMovementRepository stockMovementRepository;

  public List<BookEditionResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  public BookEditionResponse findById(UUID id) {
    BookEdition edition =
        repository.findById(id).orElseThrow(() -> new BookEditionNotFoundException(id));

    return mapper.toResponse(edition);
  }

  public BookEditionResponse create(BookEditionRequest request) {
    Book book =
        bookRepository
            .findById(request.bookId())
            .orElseThrow(() -> new BookNotFoundException(request.bookId()));

    BookEdition entity = mapper.toEntity(request, book);

    return mapper.toResponse(repository.save(entity));
  }

  public BookEditionResponse update(UUID id, BookEditionRequest request) {
    BookEdition existing =
        repository.findById(id).orElseThrow(() -> new BookEditionNotFoundException(id));

    Book book =
        bookRepository
            .findById(request.bookId())
            .orElseThrow(() -> new BookNotFoundException(request.bookId()));

    existing.setIsbn(request.isbn());
    existing.setLanguage(request.language());
    existing.setFormat(request.format());
    existing.setPageCount(request.pageCount());
    existing.setPublisher(request.publisher());
    existing.setPrice(request.price());
    existing.setPublicationDate(request.publicationDate());
    existing.setBook(book);

    return mapper.toResponse(repository.save(existing));
  }

  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new BookEditionNotFoundException(id);
    }
    repository.deleteById(id);
  }

  // stock

  public Integer getStock(UUID id) {
    if (!repository.existsById(id)) {
      throw new BookEditionNotFoundException(id);
    }

    Integer stock = stockMovementRepository.computeStock(id);
    return stock != null ? stock : 0;
  }
}
