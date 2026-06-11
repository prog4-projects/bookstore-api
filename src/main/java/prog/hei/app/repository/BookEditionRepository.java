package prog.hei.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.BookEdition;

import java.util.UUID;

public interface BookEditionRepository extends JpaRepository<BookEdition, UUID> {
}
