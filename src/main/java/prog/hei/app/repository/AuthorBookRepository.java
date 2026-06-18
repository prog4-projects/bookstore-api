package prog.hei.app.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.AuthorBook;

public interface AuthorBookRepository extends JpaRepository<AuthorBook, UUID> {
  List<AuthorBook> findByBookId(UUID bookId);

  void deleteByBookId(UUID bookId);
}
