package prog.hei.app.exception;

public class BookEditionNotFoundException extends RuntimeException {

    public BookEditionNotFoundException() {
        super("BookEdition not found");
    }

    public BookEditionNotFoundException(String message) {
        super(message);
    }

    public BookEditionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
