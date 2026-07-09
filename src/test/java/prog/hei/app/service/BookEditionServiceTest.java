package prog.hei.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.dto.bookEdition.response.BookEditionStockResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;
import prog.hei.app.exception.BookEditionNotFoundException;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.mapper.BookEditionMapper;
import prog.hei.app.repository.BookEditionRepository;
import prog.hei.app.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookEditionServiceTest {

  @Mock private BookEditionRepository repository;
  @Mock private BookRepository bookRepository;
  @Mock private BookEditionMapper mapper;

  @InjectMocks private BookEditionService service;

  private UUID editionId;
  private UUID bookId;
  private Book book;
  private BookEdition edition;
  private BookEditionRequest request;
  private BookEditionResponse response;

  @BeforeEach
  void setUp() {
    editionId = UUID.randomUUID();
    bookId = UUID.randomUUID();

    book = new Book();
    book.setId(bookId);
    book.setTitle("Clean Code");
    book.setGender(BookGenderEnum.HORROR);

    edition = new BookEdition();
    edition.setId(editionId);
    edition.setIsbn("123456789");
    edition.setLanguage(BookLanguageEnum.ENGLISH);
    edition.setFormat(BookFormatEnum.PAPERBACK);
    edition.setPageCount(200);
    edition.setPublisher("O'Reilly");
    edition.setPublicationDate(LocalDateTime.now());
    edition.setBook(book);

    request =
        new BookEditionRequest(
            "123456789",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            200,
            "O'Reilly",
            LocalDateTime.now(),
            bookId);

    response =
        new BookEditionResponse(
            editionId,
            "123456789",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            200,
            "O'Reilly",
            LocalDateTime.now(),
            bookId,
            "Clean Code");
  }

  @Test
  void findAll_shouldReturnList() {
    when(repository.findAll()).thenReturn(List.of(edition));
    when(mapper.toResponse(edition)).thenReturn(response);

    List<BookEditionResponse> result = service.findAll();

    assertThat(result).containsExactly(response);
  }

  @Test
  void findById_shouldReturnResponse_whenExists() {
    when(repository.findById(editionId)).thenReturn(Optional.of(edition));
    when(mapper.toResponse(edition)).thenReturn(response);

    BookEditionResponse result = service.findById(editionId);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void findById_shouldThrow_whenNotFound() {
    when(repository.findById(editionId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findById(editionId))
        .isInstanceOf(BookEditionNotFoundException.class);
  }

  @Test
  void getStock_shouldReturnStockResponse() {
    when(repository.findById(editionId)).thenReturn(Optional.of(edition));
    when(repository.getStockById(editionId)).thenReturn(10);

    BookEditionStockResponse result = service.getStock(editionId);

    assertThat(result.bookEditionId()).isEqualTo(editionId);
    assertThat(result.title()).isEqualTo("Clean Code");
    assertThat(result.stock()).isEqualTo(10);
  }

  @Test
  void getStock_shouldThrow_whenNotFound() {
    when(repository.findById(editionId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.getStock(editionId))
        .isInstanceOf(BookEditionNotFoundException.class);
  }

  @Test
  void create_shouldReturnResponse() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(mapper.toEntity(request, book)).thenReturn(edition);
    when(repository.save(edition)).thenReturn(edition);
    when(mapper.toResponse(edition)).thenReturn(response);

    BookEditionResponse result = service.create(request);

    assertThat(result).isEqualTo(response);
    verify(repository).save(edition);
  }

  @Test
  void create_shouldThrow_whenBookNotFound() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.create(request)).isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void update_shouldReturnResponse_whenExists() {
    when(repository.findById(editionId)).thenReturn(Optional.of(edition));
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(repository.save(edition)).thenReturn(edition);
    when(mapper.toResponse(edition)).thenReturn(response);

    BookEditionResponse result = service.update(editionId, request);

    assertThat(result).isEqualTo(response);
    assertThat(edition.getIsbn()).isEqualTo("123456789");
    assertThat(edition.getLanguage()).isEqualTo(BookLanguageEnum.ENGLISH);
    assertThat(edition.getFormat()).isEqualTo(BookFormatEnum.PAPERBACK);
    assertThat(edition.getPageCount()).isEqualTo(200);
    assertThat(edition.getPublisher()).isEqualTo("O'Reilly");
    assertThat(edition.getBook()).isEqualTo(book);
  }

  @Test
  void update_shouldThrow_whenEditionNotFound() {
    when(repository.findById(editionId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.update(editionId, request))
        .isInstanceOf(BookEditionNotFoundException.class);
  }

  @Test
  void update_shouldThrow_whenBookNotFound() {
    when(repository.findById(editionId)).thenReturn(Optional.of(edition));
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.update(editionId, request))
        .isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void delete_shouldDelete_whenExists() {
    when(repository.existsById(editionId)).thenReturn(true);

    service.delete(editionId);

    verify(repository).deleteById(editionId);
  }

  @Test
  void delete_shouldThrow_whenNotFound() {
    when(repository.existsById(editionId)).thenReturn(false);

    assertThatThrownBy(() -> service.delete(editionId))
        .isInstanceOf(BookEditionNotFoundException.class);
  }
}
