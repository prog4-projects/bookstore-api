package prog.hei.app.endpoint.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

public record BookDto(
    String id,
    String title,
    String description,
    BookGenderEnum gender,
    String isbn,
    BookLanguageEnum language,
    BookFormatEnum format,
    Integer pageCount,
    String publisher,
    BigDecimal price,
    LocalDateTime publicationDate,
    Integer stockQuantity,
    List<String> authorIds,
    List<String> libraryIds) {}
