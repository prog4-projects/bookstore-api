package prog.hei.app.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import prog.hei.app.conf.FacadeIT;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.entity.Author;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.enums.BookFormatEnum;
import prog.hei.app.entity.enums.BookGenderEnum;
import prog.hei.app.entity.enums.BookLanguageEnum;
import prog.hei.app.entity.enums.UserGenderEnum;
import prog.hei.app.repository.AuthorRepository;
import prog.hei.app.repository.LibraryRepository;

class BookControllerIT extends FacadeIT {

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private AuthorRepository authorRepository;
  @Autowired private LibraryRepository libraryRepository;

  private String authorId;
  private String libraryId;

  @BeforeEach
  void setUp() {
    Author author = new Author();
    author.setFirstName("John");
    author.setLastName("Doe");
    author.setBirthDate(LocalDate.of(1980, 1, 1));
    author.setGender(UserGenderEnum.MALE);
    author = authorRepository.save(author);
    authorId = author.getId().toString();

    Library library = new Library();
    library.setName("Central Library");
    library.setAddress("123 Main St");
    library.setPhoneNumber("0123456789");
    library = libraryRepository.save(library);
    libraryId = library.getId().toString();
  }

  @Test
  void createAndGetBook() {
    BookDto dto =
        new BookDto(
            null,
            "Test Book",
            "A test book description",
            BookGenderEnum.FANTASY,
            "978-3-16-148410-0",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            300,
            "Test Publisher",
            BigDecimal.valueOf(19.99),
            LocalDateTime.now(),
            50,
            List.of(authorId),
            List.of(libraryId));

    ResponseEntity<BookDto> createResponse =
        restTemplate.postForEntity("/books", dto, BookDto.class);
    assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
    BookDto created = createResponse.getBody();
    assertNotNull(created);
    assertNotNull(created.id());
    assertEquals("Test Book", created.title());
    assertEquals(BookGenderEnum.FANTASY, created.gender());
    assertEquals("978-3-16-148410-0", created.isbn());

    ResponseEntity<BookDto> getResponse =
        restTemplate.getForEntity("/books/" + created.id(), BookDto.class);
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    BookDto fetched = getResponse.getBody();
    assertNotNull(fetched);
    assertEquals(created.id(), fetched.id());
  }

  @Test
  void getAllBooks() {
    BookDto dto =
        new BookDto(
            null,
            "Another Book",
            "Description",
            BookGenderEnum.SCIENCE_FICTION,
            "978-0-596-52068-7",
            BookLanguageEnum.FRENCH,
            BookFormatEnum.EBOOK,
            250,
            "Publisher",
            BigDecimal.valueOf(14.99),
            LocalDateTime.now(),
            30,
            List.of(authorId),
            List.of(libraryId));
    restTemplate.postForEntity("/books", dto, BookDto.class);

    ResponseEntity<BookDto[]> response = restTemplate.getForEntity("/books", BookDto[].class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    BookDto[] books = response.getBody();
    assertNotNull(books);
    assertTrue(books.length >= 1);
  }

  @Test
  void updateBook() {
    BookDto dto =
        new BookDto(
            null,
            "Original Title",
            "Original description",
            BookGenderEnum.HORROR,
            "978-1-566-19261-5",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            200,
            "Original Publisher",
            BigDecimal.valueOf(9.99),
            LocalDateTime.now(),
            10,
            List.of(authorId),
            List.of(libraryId));
    ResponseEntity<BookDto> createResponse =
        restTemplate.postForEntity("/books", dto, BookDto.class);
    String bookId = createResponse.getBody().id();

    BookDto updateDto =
        new BookDto(
            null,
            "Updated Title",
            "Updated description",
            BookGenderEnum.THRILLER,
            null,
            null,
            null,
            null,
            "Updated Publisher",
            null,
            null,
            20,
            null,
            null);

    HttpEntity<BookDto> requestEntity = new HttpEntity<>(updateDto);
    ResponseEntity<BookDto> updateResponse =
        restTemplate.exchange("/books/" + bookId, HttpMethod.PUT, requestEntity, BookDto.class);
    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
    BookDto updated = updateResponse.getBody();
    assertNotNull(updated);
    assertEquals("Updated Title", updated.title());
    assertEquals("Updated description", updated.description());
    assertEquals(BookGenderEnum.THRILLER, updated.gender());
    assertEquals("Updated Publisher", updated.publisher());
    assertEquals(Integer.valueOf(20), updated.stockQuantity());
  }

  @Test
  void deleteBook() {
    BookDto dto =
        new BookDto(
            null,
            "Book to Delete",
            "Will be deleted",
            BookGenderEnum.MYSTERY,
            "978-0-201-63361-0",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.POCKET,
            150,
            "Publisher",
            BigDecimal.valueOf(12.99),
            LocalDateTime.now(),
            5,
            List.of(authorId),
            List.of(libraryId));
    ResponseEntity<BookDto> createResponse =
        restTemplate.postForEntity("/books", dto, BookDto.class);
    String bookId = createResponse.getBody().id();

    restTemplate.delete("/books/" + bookId);

    ResponseEntity<BookDto> getResponse =
        restTemplate.getForEntity("/books/" + bookId, BookDto.class);
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  void createBook_withoutTitle_returnsBadRequest() {
    BookDto dto =
        new BookDto(
            null,
            null,
            "No title",
            BookGenderEnum.FANTASY,
            "978-1-234-56789-0",
            BookLanguageEnum.ENGLISH,
            BookFormatEnum.PAPERBACK,
            100,
            "Publisher",
            BigDecimal.TEN,
            LocalDateTime.now(),
            10,
            List.of(authorId),
            List.of(libraryId));

    ResponseEntity<String> response = restTemplate.postForEntity("/books", dto, String.class);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void findBook_notFound_returnsNotFound() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/books/" + UUID.randomUUID(), String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
