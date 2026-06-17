package prog.hei.app.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.exception.BookEditionNotFoundException;
import prog.hei.app.repository.BookEditionRepository;

@Service
@RequiredArgsConstructor
public class BookEditionService {

  private final BookEditionRepository repository;

  public BookEdition create(BookEdition bookEdition) {
    return repository.save(bookEdition);
  }

  public List<BookEdition> getAll() {
    return repository.findAll();
  }

  public BookEdition getById(UUID id) {
    return repository.findById(id).orElseThrow(() -> new BookEditionNotFoundException(id));
  }

  public BookEdition update(UUID id, BookEdition updated) {
    BookEdition existing = getById(id);

    existing.setPrice(updated.getPrice());
    existing.setStockQuantity(updated.getStockQuantity());
    existing.setPublisher(updated.getPublisher());

    return repository.save(existing);
  }

  public void delete(UUID id) {
    BookEdition existing = getById(id);
    repository.delete(existing);
  }
}
