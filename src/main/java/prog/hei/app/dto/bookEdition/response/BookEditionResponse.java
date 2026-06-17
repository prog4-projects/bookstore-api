package prog.hei.app.dto.bookEdition.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

@Data
@Builder
public class BookEditionResponse {
    private UUID id;
    private String isbn;
    private BookLanguageEnum language;
    private BookFormatEnum format;
    private Integer pageCount;
    private String publisher;
    private BigDecimal price;
    private LocalDateTime publicationDate;
    private Integer stockQuantity;
}
