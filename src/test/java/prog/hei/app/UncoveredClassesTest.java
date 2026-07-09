package prog.hei.app;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import prog.hei.app.dto.book.response.BookAuthorResponse;
import prog.hei.app.dto.bookEdition.response.BookEditionStockResponse;
import prog.hei.app.dto.library.response.LibraryBookResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;
import prog.hei.app.entity.enums.BookStatusEnum;
import prog.hei.app.entity.enums.UserGenderEnum;
import prog.hei.app.exception.BookEditionNotFoundException;

class UncoveredClassesTest {

  @Test
  void bookStatusEnum_shouldHaveAllValues() {
    BookStatusEnum[] values = BookStatusEnum.values();
    assertThat(values).hasSize(4);
    assertThat(BookStatusEnum.valueOf("NEW")).isEqualTo(BookStatusEnum.NEW);
    assertThat(BookStatusEnum.valueOf("USED")).isEqualTo(BookStatusEnum.USED);
    assertThat(BookStatusEnum.valueOf("DAMAGED")).isEqualTo(BookStatusEnum.DAMAGED);
    assertThat(BookStatusEnum.valueOf("LOST")).isEqualTo(BookStatusEnum.LOST);
  }

  @Test
  void book_prePersist_shouldSetDates() {
    Book book = new Book();
    book.prePersist();
    assertThat(book.getCreatedAt()).isNotNull();
    assertThat(book.getUpdatedAt()).isNotNull();
  }

  @Test
  void book_preUpdate_shouldSetUpdatedAt() {
    Book book = new Book();
    book.prePersist();
    LocalDateTime beforeUpdate = book.getUpdatedAt();
    book.preUpdate();
    assertThat(book.getUpdatedAt()).isNotNull();
    assertThat(book.getUpdatedAt()).isAfterOrEqualTo(beforeUpdate);
  }

  @Test
  void bookAuthorResponse_shouldConstruct() {
    BookAuthorResponse response =
        new BookAuthorResponse(UUID.randomUUID(), "John", "Doe", UserGenderEnum.MALE);
    assertThat(response.firstName()).isEqualTo("John");
    assertThat(response.lastName()).isEqualTo("Doe");
    assertThat(response.gender()).isEqualTo(UserGenderEnum.MALE);
  }

  @Test
  void bookEditionStockResponse_shouldConstruct() {
    UUID id = UUID.randomUUID();
    BookEditionStockResponse response = new BookEditionStockResponse(id, "Title", 5);
    assertThat(response.bookEditionId()).isEqualTo(id);
    assertThat(response.title()).isEqualTo("Title");
    assertThat(response.stock()).isEqualTo(5);
  }

  @Test
  void libraryBookResponse_shouldConstruct() {
    LibraryBookResponse response =
        new LibraryBookResponse(
            UUID.randomUUID(),
            "Title",
            "Desc",
            BookGenderEnum.MYSTERY,
            "isbn",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            100,
            "Pub",
            BigDecimal.TEN,
            LocalDateTime.now());
    assertThat(response.title()).isEqualTo("Title");
    assertThat(response.description()).isEqualTo("Desc");
    assertThat(response.gender()).isEqualTo(BookGenderEnum.MYSTERY);
  }

  @Test
  void bookEditionNotFoundException_shouldHaveMessage() {
    UUID id = UUID.randomUUID();
    BookEditionNotFoundException ex = new BookEditionNotFoundException(id);
    assertThat(ex.getMessage()).contains(id.toString());
  }
}
