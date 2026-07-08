package prog.hei.app.exception;

import java.util.UUID;

public class BookEditionNotFoundException extends NotFoundException {

  public BookEditionNotFoundException(UUID id) {
    super("BookEdition not found with id: " + id);
  }
}
