package prog.hei.app.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {}
