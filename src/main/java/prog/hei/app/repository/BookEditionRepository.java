package prog.hei.app.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.BookEdition;

public interface BookEditionRepository extends JpaRepository<BookEdition, UUID> {
  void deleteByBookId(UUID bookId);
}
