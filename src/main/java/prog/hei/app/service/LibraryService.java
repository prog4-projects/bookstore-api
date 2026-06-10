package prog.hei.app.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.mapper.LibraryMapper;
import prog.hei.app.repository.LibraryRepository;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryRepository libraryRepository;
  private final LibraryMapper libraryMapper;

  public List<LibraryResponse> getAll() {
    return libraryRepository.findAll().stream().map(libraryMapper::toResponse).toList();
  }
}
