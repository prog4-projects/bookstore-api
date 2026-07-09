package prog.hei.app.exception;

import java.util.UUID;

public class AuthorNotFoundException extends NotFoundException {
  public AuthorNotFoundException(UUID id) {
    super("Author not found with id: " + id);
  }
}
