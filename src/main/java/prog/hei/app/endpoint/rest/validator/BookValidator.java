package prog.hei.app.endpoint.rest.validator;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import prog.hei.app.endpoint.rest.dto.BookDto;

@Component
public class BookValidator {

  public void validateCreate(BookDto dto) {
    if (dto.title() == null || dto.title().isBlank())
      throw new IllegalArgumentException("Title is required");
    if (dto.gender() == null) throw new IllegalArgumentException("Gender is required");
    if (dto.isbn() == null || dto.isbn().isBlank())
      throw new IllegalArgumentException("ISBN is required");
    if (dto.language() == null) throw new IllegalArgumentException("Language is required");
    if (dto.format() == null) throw new IllegalArgumentException("Format is required");
    if (dto.pageCount() == null || dto.pageCount() <= 0)
      throw new IllegalArgumentException("Page count must be > 0");
    if (dto.price() != null && dto.price().compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Price must be >= 0");
    if (dto.stockQuantity() != null && dto.stockQuantity() < 0)
      throw new IllegalArgumentException("Stock quantity must be >= 0");
    if (dto.authorIds() == null || dto.authorIds().isEmpty())
      throw new IllegalArgumentException("At least one author is required");
  }

  public void validateUpdate(BookDto dto) {
    if (dto.title() != null && dto.title().isBlank())
      throw new IllegalArgumentException("Title must not be blank");
    if (dto.pageCount() != null && dto.pageCount() <= 0)
      throw new IllegalArgumentException("Page count must be > 0");
    if (dto.price() != null && dto.price().compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("Price must be >= 0");
    if (dto.stockQuantity() != null && dto.stockQuantity() < 0)
      throw new IllegalArgumentException("Stock quantity must be >= 0");
  }
}
