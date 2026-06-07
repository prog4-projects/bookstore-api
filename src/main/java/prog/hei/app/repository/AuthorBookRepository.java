package prog.hei.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.AuthorBook;

public interface AuthorBookRepository extends JpaRepository<AuthorBook, String> {
  List<AuthorBook> findByBookId(String bookId);

  void deleteByBookId(String bookId);
}
