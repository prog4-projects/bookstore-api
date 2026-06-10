package prog.hei.app.dto.library.response;

import prog.hei.app.entity.enums.BookGenderEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record LibraryBookResponse(
        UUID bookId,
        String title,
        String description,
        BookGenderEnum gender,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
