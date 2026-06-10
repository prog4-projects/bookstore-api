package prog.hei.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.Library;

import java.util.UUID;

public interface LibraryRepository extends JpaRepository<Library, UUID> {}
