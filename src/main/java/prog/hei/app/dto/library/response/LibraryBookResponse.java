package prog.hei.app.dto.library.response;

import java.time.LocalDateTime;
import java.util.UUID;
import prog.hei.app.entity.enums.BookGenderEnum;

public record LibraryBookResponse(
    UUID bookId,
    String title,
    String description,
    BookGenderEnum gender,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
