package prog.hei.app.mapper;

import org.springframework.stereotype.Component;
import prog.hei.app.dto.author.request.AuthorRequest;
import prog.hei.app.dto.author.response.AuthorResponse;
import prog.hei.app.entity.Author;

@Component
public class AuthorMapper {

  public AuthorResponse toResponse(Author author) {
    return new AuthorResponse(
        author.getId().toString(),
        author.getFirstName(),
        author.getLastName(),
        author.getBirthDate(),
        author.getGender());
  }

  public Author toEntity(AuthorRequest request) {
    Author author = new Author();
    author.setFirstName(request.firstName());
    author.setLastName(request.lastName());
    author.setBirthDate(request.birthDate());
    author.setGender(request.gender());
    return author;
  }

  public void updateEntity(Author author, AuthorRequest request) {
    author.setFirstName(request.firstName());
    author.setLastName(request.lastName());
    author.setBirthDate(request.birthDate());
    author.setGender(request.gender());
  }
}
