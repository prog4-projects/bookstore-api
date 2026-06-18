package prog.hei.app.dto.book.response;

import java.util.List;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.enums.BookGenderEnum;

public record BookResponse(
    String id,
    String title,
    String description,
    BookGenderEnum gender,
    List<BookAuthorResponse> authors,
    List<BookEditionResponse> bookEditions) {}
