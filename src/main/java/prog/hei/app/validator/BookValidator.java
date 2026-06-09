package prog.hei.app.validator;

import org.springframework.stereotype.Component;
import prog.hei.app.endpoint.rest.dto.BookDto;

@Component
public class BookValidator {

  public void validateCreate(BookDto dto) {
    if (dto.isbn() == null || dto.isbn().isBlank()) {
      throw new IllegalArgumentException("ISBN is required");
    }
    if (dto.title() == null || dto.title().isBlank()) {
      throw new IllegalArgumentException("Title is required");
    }
    if (dto.pages() <= 0) {
      throw new IllegalArgumentException("Pages must be greater than 0");
    }
    if (dto.purchasePrice() != null && dto.purchasePrice() < 0) {
      throw new IllegalArgumentException("Purchase price must be >= 0");
    }
    if (dto.sellingPrice() != null && dto.sellingPrice() < 0) {
      throw new IllegalArgumentException("Selling price must be >= 0");
    }
    if (dto.format() == null) {
      throw new IllegalArgumentException("Format is required");
    }
    if (dto.gender() == null) {
      throw new IllegalArgumentException("Gender is required");
    }
    if (dto.authorIds() == null || dto.authorIds().isEmpty()) {
      throw new IllegalArgumentException("At least one author is required");
    }
  }

  public void validateUpdate(BookDto dto) {
    if (dto.title() != null && dto.title().isBlank()) {
      throw new IllegalArgumentException("Title must not be blank");
    }
    if (dto.pages() <= 0) {
      throw new IllegalArgumentException("Pages must be greater than 0");
    }
    if (dto.purchasePrice() != null && dto.purchasePrice() < 0) {
      throw new IllegalArgumentException("Purchase price must be >= 0");
    }
    if (dto.sellingPrice() != null && dto.sellingPrice() < 0) {
      throw new IllegalArgumentException("Selling price must be >= 0");
    }
  }
}
