package prog.hei.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.LibraryBook;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, String> {
  List<LibraryBook> findByBookId(String bookId);

  void deleteByBookId(String bookId);
}
