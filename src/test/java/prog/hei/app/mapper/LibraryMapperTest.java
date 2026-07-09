package prog.hei.app.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prog.hei.app.dto.library.request.LibraryRequest;
import prog.hei.app.dto.library.response.LibraryBookResponse;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.LibraryBook;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;

class LibraryMapperTest {

  private LibraryMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new LibraryMapper();
  }

  @Test
  void toResponse_shouldMapAllFields() {
    UUID libId = UUID.randomUUID();
    Library library = new Library();
    library.setId(libId);
    library.setName("Test Library");
    library.setAddress("123 Main St");
    library.setPhoneNumber("0380000001");
    library.setLibraryBooks(null);

    LibraryResponse result = mapper.toResponse(library);

    assertThat(result.id()).isEqualTo(libId);
    assertThat(result.name()).isEqualTo("Test Library");
    assertThat(result.address()).isEqualTo("123 Main St");
    assertThat(result.phoneNumber()).isEqualTo("0380000001");
    assertThat(result.libraryBooks()).isEmpty();
  }

  @Test
  void toResponse_shouldMapLibraryBooks() {
    UUID libId = UUID.randomUUID();
    UUID editionId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();

    Book book = new Book();
    book.setId(bookId);
    book.setTitle("Test Book");
    book.setDescription("Desc");
    book.setGender(BookGenderEnum.MYSTERY);

    BookEdition edition = new BookEdition();
    edition.setId(editionId);
    edition.setIsbn("isbn");
    edition.setLanguage(BookLanguageEnum.ENGLISH);
    edition.setFormat(BookFormatEnum.PAPERBACK);
    edition.setPageCount(100);
    edition.setPublisher("Pub");
    edition.setPrice(new BigDecimal("10.00"));
    edition.setPublicationDate(LocalDateTime.now());
    edition.setBook(book);

    LibraryBook lb = new LibraryBook();
    lb.setBookEdition(edition);

    Library library = new Library();
    library.setId(libId);
    library.setName("Lib");
    library.setAddress("Addr");
    library.setPhoneNumber("0380000001");
    library.setLibraryBooks(new ArrayList<>(List.of(lb)));

    LibraryResponse result = mapper.toResponse(library);

    assertThat(result.libraryBooks()).hasSize(1);
    assertThat(result.libraryBooks().get(0).bookEditionId()).isEqualTo(editionId);
    assertThat(result.libraryBooks().get(0).title()).isEqualTo("Test Book");
  }

  @Test
  void toEntity_shouldMapFromRequest() {
    LibraryRequest request = new LibraryRequest("My Lib", "123 Street", "0380000001");

    Library result = mapper.toEntity(request);

    assertThat(result.getName()).isEqualTo("My Lib");
    assertThat(result.getAddress()).isEqualTo("123 Street");
    assertThat(result.getPhoneNumber()).isEqualTo("0380000001");
  }

  @Test
  void updateEntity_shouldUpdateFields() {
    Library library = new Library();
    library.setName("Old");
    library.setAddress("Old Addr");
    library.setPhoneNumber("0380000000");

    LibraryRequest request = new LibraryRequest("New", "New Addr", "0380000099");

    mapper.updateEntity(library, request);

    assertThat(library.getName()).isEqualTo("New");
    assertThat(library.getAddress()).isEqualTo("New Addr");
    assertThat(library.getPhoneNumber()).isEqualTo("0380000099");
  }

  @Test
  void toLibraryBookResponse_shouldMapAllFields() {
    UUID editionId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();

    Book book = new Book();
    book.setId(bookId);
    book.setTitle("Title");
    book.setDescription("Desc");
    book.setGender(BookGenderEnum.HORROR);

    BookEdition edition = new BookEdition();
    edition.setId(editionId);
    edition.setIsbn("isbn");
    edition.setLanguage(BookLanguageEnum.FRENCH);
    edition.setFormat(BookFormatEnum.POCKET);
    edition.setPageCount(300);
    edition.setPublisher("Pub");
    edition.setPrice(new BigDecimal("30.00"));
    edition.setPublicationDate(LocalDateTime.of(2024, 1, 1, 0, 0));
    edition.setBook(book);

    LibraryBook lb = new LibraryBook();
    lb.setBookEdition(edition);

    LibraryBookResponse result = mapper.toLibraryBookResponse(lb);

    assertThat(result.bookEditionId()).isEqualTo(editionId);
    assertThat(result.title()).isEqualTo("Title");
    assertThat(result.description()).isEqualTo("Desc");
    assertThat(result.gender()).isEqualTo(BookGenderEnum.HORROR);
    assertThat(result.isbn()).isEqualTo("isbn");
    assertThat(result.language()).isEqualTo(BookLanguageEnum.FRENCH);
    assertThat(result.format()).isEqualTo(BookFormatEnum.POCKET);
    assertThat(result.pageCount()).isEqualTo(300);
    assertThat(result.publisher()).isEqualTo("Pub");
    assertThat(result.price()).isEqualByComparingTo(new BigDecimal("30.00"));
    assertThat(result.publicationDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0));
  }

  @Test
  void toLibraryBookResponses_shouldReturnEmptyList_whenNull() {
    Library library = new Library();
    library.setId(UUID.randomUUID());
    library.setName("Lib");
    library.setAddress("Addr");
    library.setPhoneNumber("0380000001");
    library.setLibraryBooks(null);

    LibraryResponse result = mapper.toResponse(library);

    assertThat(result.libraryBooks()).isEmpty();
  }
}
