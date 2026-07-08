package prog.hei.app.dto.bookEdition.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

public record BookEditionResponse(
    UUID id,
    String isbn,
    BookLanguageEnum language,
    BookFormatEnum format,
    Integer pageCount,
    String publisher,
    BigDecimal price,
    LocalDateTime publicationDate,
    UUID bookId,
    String bookTitle) {}
