package prog.hei.app.dto.bookEdition.request;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

public record BookEditionRequest(
    @NotBlank(message = "ISBN is required") String isbn,
    @NotNull(message = "Language is required") BookLanguageEnum language,
    BookFormatEnum format,
    @NotNull @Positive Integer pageCount,
    String publisher,
    @NotNull LocalDateTime publicationDate,
    @NotNull UUID bookId) {}
