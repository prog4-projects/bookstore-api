package prog.hei.app.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

class BookEditionMapperTest {

  private BookEditionMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new BookEditionMapper();
  }

  @Test
  void toEntity_shouldMapAllFields() {
    UUID bookId = UUID.randomUUID();
    Book book = new Book();
    book.setId(bookId);

    BookEditionRequest request =
        new BookEditionRequest(
            "isbn-123",
            BookLanguageEnum.FRENCH,
            BookFormatEnum.POCKET,
            300,
            "Gallimard",
            new BigDecimal("25.00"),
            LocalDateTime.of(2024, 1, 15, 0, 0),
            bookId);

    BookEdition result = mapper.toEntity(request, book);

    assertThat(result.getIsbn()).isEqualTo("isbn-123");
    assertThat(result.getLanguage()).isEqualTo(BookLanguageEnum.FRENCH);
    assertThat(result.getFormat()).isEqualTo(BookFormatEnum.POCKET);
    assertThat(result.getPageCount()).isEqualTo(300);
    assertThat(result.getPublisher()).isEqualTo("Gallimard");
    assertThat(result.getPrice()).isEqualByComparingTo(new BigDecimal("25.00"));
    assertThat(result.getPublicationDate()).isEqualTo(LocalDateTime.of(2024, 1, 15, 0, 0));
    assertThat(result.getBook()).isEqualTo(book);
  }

  @Test
  void toResponse_shouldMapAllFields() {
    UUID editionId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    Book book = new Book();
    book.setId(bookId);
    book.setTitle("Test Book");

    BookEdition edition = new BookEdition();
    edition.setId(editionId);
    edition.setIsbn("isbn-456");
    edition.setLanguage(BookLanguageEnum.ENGLISH);
    edition.setFormat(BookFormatEnum.PAPERBACK);
    edition.setPageCount(150);
    edition.setPublisher("O'Reilly");
    edition.setPrice(new BigDecimal("19.99"));
    edition.setPublicationDate(LocalDateTime.of(2024, 6, 1, 0, 0));
    edition.setBook(book);

    BookEditionResponse result = mapper.toResponse(edition);

    assertThat(result.id()).isEqualTo(editionId);
    assertThat(result.isbn()).isEqualTo("isbn-456");
    assertThat(result.language()).isEqualTo(BookLanguageEnum.ENGLISH);
    assertThat(result.format()).isEqualTo(BookFormatEnum.PAPERBACK);
    assertThat(result.pageCount()).isEqualTo(150);
    assertThat(result.publisher()).isEqualTo("O'Reilly");
    assertThat(result.price()).isEqualByComparingTo(new BigDecimal("19.99"));
    assertThat(result.publicationDate()).isEqualTo(LocalDateTime.of(2024, 6, 1, 0, 0));
    assertThat(result.bookId()).isEqualTo(bookId);
    assertThat(result.bookTitle()).isEqualTo("Test Book");
  }

  @Test
  void toResponseList_shouldReturnEmptyList_whenNull() {
    List<BookEditionResponse> result = mapper.toResponseList(null);

    assertThat(result).isEmpty();
  }

  @Test
  void toResponseList_shouldMapList() {
    UUID bookId = UUID.randomUUID();
    Book book = new Book();
    book.setId(bookId);
    book.setTitle("Book");

    BookEdition edition = new BookEdition();
    edition.setId(UUID.randomUUID());
    edition.setIsbn("isbn");
    edition.setLanguage(BookLanguageEnum.ENGLISH);
    edition.setPageCount(100);
    edition.setPrice(BigDecimal.TEN);
    edition.setPublicationDate(LocalDateTime.now());
    edition.setBook(book);

    List<BookEditionResponse> result = mapper.toResponseList(List.of(edition));

    assertThat(result).hasSize(1);
  }
}
