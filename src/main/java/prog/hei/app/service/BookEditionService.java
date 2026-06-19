package prog.hei.app.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.exception.BookEditionNotFoundException;
import prog.hei.app.repository.BookEditionRepository;
import prog.hei.app.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookEditionService {

  private final BookEditionRepository repository;
  private final BookRepository bookRepository;

  public List<BookEdition> findAll() {
    return repository.findAll();
  }

  public BookEdition findById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new BookEditionNotFoundException(id));
  }

  public BookEdition create(BookEdition edition) {
    return repository.save(edition);
  }

  public BookEdition update(UUID id, BookEdition updated) {
    BookEdition existing = findById(id);

    existing.setIsbn(updated.getIsbn());
    existing.setLanguage(updated.getLanguage());
    existing.setFormat(updated.getFormat());
    existing.setPageCount(updated.getPageCount());
    existing.setPublisher(updated.getPublisher());
    existing.setPrice(updated.getPrice());
    existing.setPublicationDate(updated.getPublicationDate());
    existing.setBook(updated.getBook());

    return repository.save(existing);
  }

  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new BookEditionNotFoundException(id);
    }
    repository.deleteById(id);
  }
}
