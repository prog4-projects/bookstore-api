package prog.hei.app.dto.stock.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

public record LowStockEditionResponse(
    UUID editionId,
    UUID bookId,
    String bookTitle,
    String isbn,
    BookLanguageEnum language,
    BookFormatEnum format,
    Integer pageCount,
    String publisher,
    BigDecimal price,
    LocalDateTime publicationDate,
    Integer stock) {}
