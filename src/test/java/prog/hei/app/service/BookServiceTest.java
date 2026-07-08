package prog.hei.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.dto.book.response.BookStockResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.exception.BookNotFoundException;
import prog.hei.app.mapper.BookMapper;
import prog.hei.app.repository.AuthorBookRepository;
import prog.hei.app.repository.BookEditionRepository;
import prog.hei.app.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
  @Mock private BookRepository bookRepository;
  @Mock private BookEditionRepository bookEditionRepository;
  @Mock private AuthorBookRepository authorBookRepository;
  @Mock private BookMapper bookMapper;

  @InjectMocks private BookService bookService;

  private UUID id;
  private Book book;
  private BookRequest request;
  private BookResponse response;
  private BookStockResponse stockResponse;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    book = new Book();
    book.setId(id);
    book.setTitle("Test Title");
    book.setDescription("Description");
    book.setGender(BookGenderEnum.ROMANCE);

    request = new BookRequest("Test Title", "Description", BookGenderEnum.ROMANCE);
    response =
        new BookResponse(id, "Test Title", "Description", BookGenderEnum.ROMANCE, null, null);
    stockResponse = new BookStockResponse(id, "Test Title", 42);
  }

  @Test
  void findAll_shouldReturnMappedResponses_whenBooksExist() {
    List<Book> books = List.of(book);
    when(bookRepository.findAll()).thenReturn(books);
    when(bookMapper.toResponse(book)).thenReturn(response);

    List<BookResponse> results = bookService.findAll();

    assertThat(results).containsExactly(response);
    verify(bookRepository).findAll();
  }

  @Test
  void findById_shouldReturnMappedResponse_whenBookExists() {
    when(bookRepository.findById(id)).thenReturn(Optional.of(book));
    when(bookMapper.toResponse(book)).thenReturn(response);

    BookResponse result = bookService.findById(id);

    assertThat(result).isEqualTo(response);
    verify(bookRepository).findById(id);
  }

  @Test
  void findById_shouldThrowException_whenBookDoesNotExist() {
    when(bookRepository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.findById(id)).isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void create_shouldReturnMappedResponse_whenSaved() {
    when(bookMapper.toEntity(request)).thenReturn(book);
    when(bookRepository.save(book)).thenReturn(book);
    when(bookMapper.toResponse(book)).thenReturn(response);

    BookResponse result = bookService.create(request);

    assertThat(result).isEqualTo(response);
    verify(bookMapper).toEntity(request);
    verify(bookRepository).save(book);
  }

  @Test
  void update_shouldReturnMappedResponse_whenBookExists() {
    when(bookRepository.findById(id)).thenReturn(Optional.of(book));
    when(bookMapper.toResponse(book)).thenReturn(response);

    BookResponse result = bookService.update(id, request);

    assertThat(result).isEqualTo(response);
    assertThat(book.getTitle()).isEqualTo(request.title());
    assertThat(book.getDescription()).isEqualTo(request.description());
    assertThat(book.getGender()).isEqualTo(request.gender());
  }

  @Test
  void update_shouldThrowException_whenBookDoesNotExist() {
    when(bookRepository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.update(id, request))
        .isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void delete_shouldDeleteBook_whenBookExists() {
    when(bookRepository.findById(id)).thenReturn(Optional.of(book));
    bookService.delete(id);
    verify(bookRepository).delete(book);
  }

  @Test
  void delete_shouldThrowException_whenBookDoesNotExist() {
    when(bookRepository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.delete(id)).isInstanceOf(BookNotFoundException.class);
  }

  @Test
  void getStock_shouldReturnStockResponse_whenBookExists() {
    when(bookRepository.findById(id)).thenReturn(Optional.of(book));
    when(bookRepository.getStockById(id)).thenReturn(42);

    BookStockResponse result = bookService.getStock(id);

    assertThat(result.id()).isEqualTo(id);
    assertThat(result.title()).isEqualTo("Test Title");
    assertThat(result.stock()).isEqualTo(42);
  }

  @Test
  void getStock_shouldThrowException_whenBookDoesNotExist() {
    when(bookRepository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.getStock(id)).isInstanceOf(BookNotFoundException.class);
  }
}
