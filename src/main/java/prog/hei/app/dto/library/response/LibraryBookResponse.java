package prog.hei.app.dto.library.response;

import java.time.LocalDateTime;
import java.util.UUID;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

public record LibraryBookResponse(
    UUID bookEditionId,
    String title,
    String description,
    BookGenderEnum gender,
    String isbn,
    BookLanguageEnum language,
    BookFormatEnum format,
    Integer pageCount,
    String publisher,
    LocalDateTime publicationDate) {}
