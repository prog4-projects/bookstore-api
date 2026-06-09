package prog.hei.app.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import prog.hei.app.endpoint.rest.dto.BookDto;
import prog.hei.app.entity.Author;
import prog.hei.app.entity.AuthorBook;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.LibraryBook;

@Component
public class BookMapper {

  public BookDto toDto(Book book) {
    List<String> authorIds =
        book.getAuthorBooks() != null
            ? book.getAuthorBooks().stream()
                .map(ab -> ab.getAuthor().getId())
                .collect(Collectors.toList())
            : Collections.emptyList();

    List<String> libraryIds =
        book.getLibraryBooks() != null
            ? book.getLibraryBooks().stream()
                .map(lb -> lb.getLibrary().getId())
                .collect(Collectors.toList())
            : Collections.emptyList();

    return new BookDto(
        book.getId(),
        book.getIsbn(),
        book.getTitle(),
        book.getPublisher(),
        book.getDescription(),
        book.getPages(),
        book.getFormat(),
        book.getGender(),
        book.getPurchasePrice(),
        book.getSellingPrice(),
        book.getPublicationDate(),
        authorIds,
        libraryIds);
  }

  public Book toEntity(BookDto dto, List<Author> authors, List<Library> libraries) {
    Book book = new Book();
    book.setId(dto.id());
    book.setIsbn(dto.isbn());
    book.setTitle(dto.title());
    book.setPublisher(dto.publisher());
    book.setDescription(dto.description());
    book.setPages(dto.pages());
    book.setFormat(dto.format());
    book.setGender(dto.gender());
    book.setPurchasePrice(dto.purchasePrice());
    book.setSellingPrice(dto.sellingPrice());
    book.setPublicationDate(dto.publicationDate());

    List<AuthorBook> authorBooks = new ArrayList<>();
    if (authors != null) {
      for (Author author : authors) {
        AuthorBook ab = new AuthorBook();
        ab.setAuthor(author);
        ab.setBook(book);
        authorBooks.add(ab);
      }
    }
    book.setAuthorBooks(authorBooks);

    List<LibraryBook> libraryBooks = new ArrayList<>();
    if (libraries != null) {
      for (Library library : libraries) {
        LibraryBook lb = new LibraryBook();
        lb.setLibrary(library);
        lb.setBook(book);
        libraryBooks.add(lb);
      }
    }
    book.setLibraryBooks(libraryBooks);

    return book;
  }
}
