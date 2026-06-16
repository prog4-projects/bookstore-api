package prog.hei.app.exception;

import java.util.UUID;

public class LibraryNotFoundException extends NotFoundException {
  public LibraryNotFoundException(UUID id) {
    super("Library not fount with id: " + id);
  }
}
