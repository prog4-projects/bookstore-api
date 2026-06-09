package prog.hei.app.endpoint.rest.dto;

import java.time.LocalDate;
import java.util.List;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;

public record BookDto(
    String id,
    String isbn,
    String title,
    String publisher,
    String description,
    int pages,
    BookFormatEnum format,
    BookGenderEnum gender,
    Double purchasePrice,
    Double sellingPrice,
    LocalDate publicationDate,
    List<String> authorIds,
    List<String> libraryIds) {}
