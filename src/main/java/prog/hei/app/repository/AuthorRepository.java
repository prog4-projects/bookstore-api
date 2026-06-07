package prog.hei.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {}
