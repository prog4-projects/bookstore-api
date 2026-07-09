package prog.hei.app.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.Author;
import prog.hei.app.entity.AuthorBook;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.UserGenderEnum;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

  @Mock private BookEditionMapper bookEditionMapper;

  @InjectMocks private BookMapper bookMapper;

  private Book book;
  private UUID bookId;

  @BeforeEach
  void setUp() {
    bookId = UUID.randomUUID();
    book = new Book();
    book.setId(bookId);
    book.setTitle("Test Title");
    book.setDescription("Description");
    book.setGender(BookGenderEnum.MYSTERY);
  }

  @Test
  void toResponse_shouldMapAllFields() {
    BookEditionResponse editionResponse =
        new BookEditionResponse(
            UUID.randomUUID(),
            "isbn",
            null,
            null,
            100,
            "pub",
            LocalDateTime.now(),
            bookId,
            "Test Title");
    when(bookEditionMapper.toResponseList(null)).thenReturn(List.of(editionResponse));

    BookResponse result = bookMapper.toResponse(book);

    assertThat(result.id()).isEqualTo(bookId);
    assertThat(result.title()).isEqualTo("Test Title");
    assertThat(result.description()).isEqualTo("Description");
    assertThat(result.gender()).isEqualTo(BookGenderEnum.MYSTERY);
    assertThat(result.authors()).isEmpty();
    assertThat(result.bookEditions()).containsExactly(editionResponse);
  }

  @Test
  void toResponse_shouldMapAuthorBooks() {
    Author author = new Author();
    author.setId(UUID.randomUUID());
    author.setFirstName("John");
    author.setLastName("Doe");
    author.setGender(UserGenderEnum.MALE);

    AuthorBook ab = new AuthorBook();
    ab.setAuthor(author);
    ab.setBook(book);
    book.setAuthorBooks(new ArrayList<>(List.of(ab)));

    when(bookEditionMapper.toResponseList(null)).thenReturn(List.of());

    BookResponse result = bookMapper.toResponse(book);

    assertThat(result.authors()).hasSize(1);
    assertThat(result.authors().get(0).firstName()).isEqualTo("John");
    assertThat(result.authors().get(0).lastName()).isEqualTo("Doe");
    assertThat(result.authors().get(0).gender()).isEqualTo(UserGenderEnum.MALE);
  }

  @Test
  void toResponse_shouldReturnEmptyAuthors_whenNull() {
    book.setAuthorBooks(null);
    when(bookEditionMapper.toResponseList(null)).thenReturn(List.of());

    BookResponse result = bookMapper.toResponse(book);

    assertThat(result.authors()).isEmpty();
  }

  @Test
  void toEntity_shouldMapFromRequest() {
    BookRequest request = new BookRequest("Title", "Desc", BookGenderEnum.ROMANCE);

    Book result = bookMapper.toEntity(request);

    assertThat(result.getTitle()).isEqualTo("Title");
    assertThat(result.getDescription()).isEqualTo("Desc");
    assertThat(result.getGender()).isEqualTo(BookGenderEnum.ROMANCE);
  }

  @Test
  void updateEntity_shouldUpdateFields() {
    BookRequest request = new BookRequest("New Title", "New Desc", BookGenderEnum.FANTASY);

    bookMapper.updateEntity(book, request);

    assertThat(book.getTitle()).isEqualTo("New Title");
    assertThat(book.getDescription()).isEqualTo("New Desc");
    assertThat(book.getGender()).isEqualTo(BookGenderEnum.FANTASY);
  }
}
