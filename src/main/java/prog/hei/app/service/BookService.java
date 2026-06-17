package prog.hei.app.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.endpoint.rest.mapper.BookMapper;
import prog.hei.app.endpoint.rest.validator.BookValidator;
import prog.hei.app.entity.Author;
import prog.hei.app.entity.AuthorBook;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.LibraryBook;
import prog.hei.app.repository.AuthorBookRepository;
import prog.hei.app.repository.AuthorRepository;
import prog.hei.app.repository.BookEditionRepository;
import prog.hei.app.repository.BookRepository;
import prog.hei.app.repository.LibraryBookRepository;
import prog.hei.app.repository.LibraryRepository;

@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final BookEditionRepository bookEditionRepository;
  private final AuthorRepository authorRepository;
  private final LibraryRepository libraryRepository;
  private final AuthorBookRepository authorBookRepository;
  private final LibraryBookRepository libraryBookRepository;
  private final BookMapper bookMapper;
  private final BookValidator bookValidator;

  @Transactional(readOnly = true)
  public List<BookDto> findAll() {
    return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
  }

  @Transactional(readOnly = true)
  public BookDto findById(String id) {
    Book book =
        bookRepository
            .findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    return bookMapper.toDto(book);
  }

  @Transactional
  public BookDto create(BookDto dto) {
    bookValidator.validateCreate(dto);
    Book book = bookMapper.toEntity(dto);
    book = bookRepository.save(book);
    BookEdition edition = bookMapper.toEditionEntity(dto, book);
    bookEditionRepository.save(edition);
    book.setBookEditions(List.of(edition));
    List<Author> authors = resolveAuthors(dto.authorIds());
    List<Library> libraries = resolveLibraries(dto.libraryIds());
    saveAuthorBooks(book, authors);
    saveLibraryBooks(book, libraries);
    return bookMapper.toDto(book);
  }

  @Transactional
  public BookDto update(String id, BookDto dto) {
    bookValidator.validateUpdate(dto);
    UUID uuid = UUID.fromString(id);
    Book existing =
        bookRepository
            .findById(uuid)
            .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    if (dto.title() != null) existing.setTitle(dto.title());
    if (dto.description() != null) existing.setDescription(dto.description());
    if (dto.gender() != null) existing.setGender(dto.gender());
    handleEditionUpdate(existing, dto);
    if (dto.authorIds() != null) {
      authorBookRepository.deleteByBookId(uuid);
      List<Author> authors = resolveAuthors(dto.authorIds());
      saveAuthorBooks(existing, authors);
    }
    if (dto.libraryIds() != null) {
      libraryBookRepository.deleteByBookId(uuid);
      List<Library> libraries = resolveLibraries(dto.libraryIds());
      saveLibraryBooks(existing, libraries);
    }
    bookRepository.save(existing);
    return bookMapper.toDto(existing);
  }

  @Transactional
  public void delete(String id) {
    UUID uuid = UUID.fromString(id);
    Book book =
        bookRepository
            .findById(uuid)
            .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    if (book.getBookEditions() != null) {
      bookEditionRepository.deleteAll(book.getBookEditions());
    }
    authorBookRepository.deleteByBookId(uuid);
    libraryBookRepository.deleteByBookId(uuid);
    bookRepository.deleteById(uuid);
  }

  private void handleEditionUpdate(Book existing, BookDto dto) {
    boolean hasEditionFields =
        dto.isbn() != null
            || dto.language() != null
            || dto.format() != null
            || dto.pageCount() != null
            || dto.publisher() != null
            || dto.price() != null
            || dto.publicationDate() != null
            || dto.stockQuantity() != null;
    if (!hasEditionFields) return;
    BookEdition edition =
        existing.getBookEditions() != null && !existing.getBookEditions().isEmpty()
            ? existing.getBookEditions().get(0)
            : new BookEdition();
    boolean isNew = edition.getId() == null;
    bookMapper.updateEditionFromDto(edition, dto);
    if (isNew) {
      edition.setBook(existing);
      bookEditionRepository.save(edition);
      existing.setBookEditions(List.of(edition));
    } else {
      bookEditionRepository.save(edition);
    }
  }

  private List<Author> resolveAuthors(List<String> authorIds) {
    if (authorIds == null || authorIds.isEmpty()) return List.of();
    List<UUID> uuids = authorIds.stream().map(UUID::fromString).toList();
    List<Author> authors = authorRepository.findAllById(uuids);
    if (authors.size() != uuids.size())
      throw new EntityNotFoundException("One or more authors not found");
    return authors;
  }

  private List<Library> resolveLibraries(List<String> libraryIds) {
    if (libraryIds == null || libraryIds.isEmpty()) return List.of();
    List<UUID> uuids = libraryIds.stream().map(UUID::fromString).toList();
    List<Library> libraries = libraryRepository.findAllById(uuids);
    if (libraries.size() != uuids.size())
      throw new EntityNotFoundException("One or more libraries not found");
    return libraries;
  }

  private void saveAuthorBooks(Book book, List<Author> authors) {
    for (Author author : authors) {
      AuthorBook ab = new AuthorBook();
      ab.setAuthor(author);
      ab.setBook(book);
      authorBookRepository.save(ab);
    }
  }

  private void saveLibraryBooks(Book book, List<Library> libraries) {
    for (Library library : libraries) {
      LibraryBook lb = new LibraryBook();
      lb.setLibrary(library);
      lb.setBook(book);
      libraryBookRepository.save(lb);
    }
  }
}
