package prog.hei.app.service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.dto.author.request.AuthorRequest;
import prog.hei.app.dto.author.response.AuthorResponse;
import prog.hei.app.entity.Author;
import prog.hei.app.exception.AuthorNotFoundException;
import prog.hei.app.mapper.AuthorMapper;
import prog.hei.app.repository.AuthorRepository;

@Service
@AllArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  @Transactional(readOnly = true)
  public List<AuthorResponse> findAll() {
    return authorRepository.findAll().stream().map(authorMapper::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public AuthorResponse findById(UUID id) {
    Author author =
        authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

    return authorMapper.toResponse(author);
  }

  @Transactional
  public AuthorResponse create(AuthorRequest request) {
    Author author = authorRepository.save(authorMapper.toEntity(request));

    return authorMapper.toResponse(author);
  }

  @Transactional
  public AuthorResponse update(UUID id, AuthorRequest request) {
    Author author =
        authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

    authorMapper.updateEntity(author, request);

    return authorMapper.toResponse(author);
  }

  @Transactional
  public void delete(UUID id) {
    Author author =
        authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

    authorRepository.delete(author);
  }
}
