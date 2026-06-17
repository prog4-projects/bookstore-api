package prog.hei.app.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.LibraryBook;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, UUID> {
  List<LibraryBook> findByBookId(UUID bookId);

  void deleteByBookId(UUID bookId);
}
