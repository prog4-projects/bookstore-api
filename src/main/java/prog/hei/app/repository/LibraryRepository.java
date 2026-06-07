package prog.hei.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prog.hei.app.entity.Library;

public interface LibraryRepository extends JpaRepository<Library, String> {}
