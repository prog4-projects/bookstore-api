package prog.hei.app.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.entity.Author;
import prog.hei.app.entity.AuthorBook;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.LibraryBook;
import prog.hei.app.mapper.BookMapper;
import prog.hei.app.repository.AuthorBookRepository;
import prog.hei.app.repository.AuthorRepository;
import prog.hei.app.repository.BookRepository;
import prog.hei.app.repository.LibraryBookRepository;
import prog.hei.app.repository.LibraryRepository;
import prog.hei.app.validator.BookValidator;

@Service
@AllArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final LibraryRepository libraryRepository;
  private final AuthorBookRepository authorBookRepository;
  private final LibraryBookRepository libraryBookRepository;
  private final BookMapper bookMapper;
  private final BookValidator bookValidator;

  public List<BookDto> findAll() {
    return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
  }

  public BookDto findById(String id) {
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    return bookMapper.toDto(book);
  }

  @Transactional
  public BookDto create(BookDto dto) {
    bookValidator.validateCreate(dto);

    List<Author> authors = resolveAuthors(dto.authorIds());
    List<Library> libraries = resolveLibraries(dto.libraryIds());

    Book book = bookMapper.toEntity(dto, authors, libraries);
    book.setId(null);
    book = bookRepository.save(book);

    saveAuthorBooks(book, authors);
    saveLibraryBooks(book, libraries);

    return bookMapper.toDto(bookRepository.findById(book.getId()).orElseThrow());
  }

  @Transactional
  public BookDto update(String id, BookDto dto) {
    bookValidator.validateUpdate(dto);

    Book existing =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));

    if (dto.isbn() != null) existing.setIsbn(dto.isbn());
    if (dto.title() != null) existing.setTitle(dto.title());
    if (dto.publisher() != null) existing.setPublisher(dto.publisher());
    if (dto.description() != null) existing.setDescription(dto.description());
    if (dto.pages() > 0) existing.setPages(dto.pages());
    if (dto.format() != null) existing.setFormat(dto.format());
    if (dto.gender() != null) existing.setGender(dto.gender());
    if (dto.purchasePrice() != null) existing.setPurchasePrice(dto.purchasePrice());
    if (dto.sellingPrice() != null) existing.setSellingPrice(dto.sellingPrice());
    if (dto.publicationDate() != null) existing.setPublicationDate(dto.publicationDate());

    if (dto.authorIds() != null) {
      authorBookRepository.deleteByBookId(id);
      List<Author> authors = resolveAuthors(dto.authorIds());
      saveAuthorBooks(existing, authors);
    }

    if (dto.libraryIds() != null) {
      libraryBookRepository.deleteByBookId(id);
      List<Library> libraries = resolveLibraries(dto.libraryIds());
      saveLibraryBooks(existing, libraries);
    }

    bookRepository.save(existing);
    return bookMapper.toDto(existing);
  }

  @Transactional
  public void delete(String id) {
    if (!bookRepository.existsById(id)) {
      throw new EntityNotFoundException("Book not found: " + id);
    }
    authorBookRepository.deleteByBookId(id);
    libraryBookRepository.deleteByBookId(id);
    bookRepository.deleteById(id);
  }

  private List<Author> resolveAuthors(List<String> authorIds) {
    if (authorIds == null) return List.of();
    List<Author> authors = authorRepository.findAllById(authorIds);
    if (authors.size() != authorIds.size()) {
      throw new EntityNotFoundException("One or more authors not found");
    }
    return authors;
  }

  private List<Library> resolveLibraries(List<String> libraryIds) {
    if (libraryIds == null) return List.of();
    List<Library> libraries = libraryRepository.findAllById(libraryIds);
    if (libraries.size() != libraryIds.size()) {
      throw new EntityNotFoundException("One or more libraries not found");
    }
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
