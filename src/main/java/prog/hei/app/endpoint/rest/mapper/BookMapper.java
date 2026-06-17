package prog.hei.app.endpoint.rest.mapper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;

@Component
public class BookMapper {

  public BookDto toDto(Book book) {
    BookEdition edition =
        book.getBookEditions() != null && !book.getBookEditions().isEmpty()
            ? book.getBookEditions().get(0)
            : null;

    List<String> authorIds =
        book.getAuthorBooks() != null
            ? book.getAuthorBooks().stream()
                .map(ab -> ab.getAuthor().getId().toString())
                .collect(Collectors.toList())
            : Collections.emptyList();

    List<String> libraryIds =
        book.getLibraryBooks() != null
            ? book.getLibraryBooks().stream()
                .map(lb -> lb.getLibrary().getId().toString())
                .collect(Collectors.toList())
            : Collections.emptyList();

    return new BookDto(
        book.getId() != null ? book.getId().toString() : null,
        book.getTitle(),
        book.getDescription(),
        book.getGender(),
        edition != null ? edition.getIsbn() : null,
        edition != null ? edition.getLanguage() : null,
        edition != null ? edition.getFormat() : null,
        edition != null ? edition.getPageCount() : null,
        edition != null ? edition.getPublisher() : null,
        edition != null ? edition.getPrice() : null,
        edition != null ? edition.getPublicationDate() : null,
        edition != null ? edition.getStockQuantity() : null,
        authorIds,
        libraryIds);
  }

  public Book toEntity(BookDto dto) {
    Book book = new Book();
    if (dto.id() != null) {
      book.setId(UUID.fromString(dto.id()));
    }
    book.setTitle(dto.title());
    book.setDescription(dto.description());
    book.setGender(dto.gender());
    return book;
  }

  public BookEdition toEditionEntity(BookDto dto, Book book) {
    BookEdition edition = new BookEdition();
    edition.setIsbn(dto.isbn());
    edition.setLanguage(dto.language());
    edition.setFormat(dto.format());
    edition.setPageCount(dto.pageCount());
    edition.setPublisher(dto.publisher());
    edition.setPrice(dto.price());
    edition.setPublicationDate(dto.publicationDate());
    edition.setStockQuantity(dto.stockQuantity());
    edition.setBook(book);
    return edition;
  }

  public void updateEditionFromDto(BookEdition edition, BookDto dto) {
    if (dto.isbn() != null) edition.setIsbn(dto.isbn());
    if (dto.language() != null) edition.setLanguage(dto.language());
    if (dto.format() != null) edition.setFormat(dto.format());
    if (dto.pageCount() != null) edition.setPageCount(dto.pageCount());
    if (dto.publisher() != null) edition.setPublisher(dto.publisher());
    if (dto.price() != null) edition.setPrice(dto.price());
    if (dto.publicationDate() != null) edition.setPublicationDate(dto.publicationDate());
    if (dto.stockQuantity() != null) edition.setStockQuantity(dto.stockQuantity());
  }
}
