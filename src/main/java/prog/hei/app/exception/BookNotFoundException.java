package prog.hei.app.exception;

import java.util.UUID;

public class BookNotFoundException extends NotFoundException {
  public BookNotFoundException(UUID id) {
    super("Book not found with id: " + id);
  }
}
