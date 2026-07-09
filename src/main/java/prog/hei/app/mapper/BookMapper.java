package prog.hei.app.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import prog.hei.app.dto.book.request.BookRequest;
import prog.hei.app.dto.book.response.BookAuthorResponse;
import prog.hei.app.dto.book.response.BookResponse;
import prog.hei.app.entity.AuthorBook;
import prog.hei.app.entity.Book;

@Component
@RequiredArgsConstructor
public class BookMapper {

  private final BookEditionMapper bookEditionMapper;

  public BookResponse toResponse(Book book) {
    return new BookResponse(
        book.getId(),
        book.getTitle(),
        book.getDescription(),
        book.getGender(),
        toAuthorResponses(book.getAuthorBooks()),
        bookEditionMapper.toResponseList(book.getBookEditions()));
  }

  public Book toEntity(BookRequest request) {
    Book book = new Book();
    book.setTitle(request.title());
    book.setDescription(request.description());
    book.setGender(request.gender());
    return book;
  }

  public void updateEntity(Book book, BookRequest request) {
    book.setTitle(request.title());
    book.setDescription(request.description());
    book.setGender(request.gender());
  }

  private List<BookAuthorResponse> toAuthorResponses(List<AuthorBook> authorBooks) {
    if (authorBooks == null) return Collections.emptyList();

    return authorBooks.stream()
        .map(
            ab ->
                new BookAuthorResponse(
                    ab.getAuthor().getId(),
                    ab.getAuthor().getFirstName(),
                    ab.getAuthor().getLastName(),
                    ab.getAuthor().getGender()))
        .collect(Collectors.toList());
  }
}
