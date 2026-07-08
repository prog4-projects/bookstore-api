package prog.hei.app.mapper;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.Book;
import prog.hei.app.entity.BookEdition;

@Component
public class BookEditionMapper {

  public BookEdition toEntity(BookEditionRequest request, Book book) {
    BookEdition edition = new BookEdition();
    edition.setIsbn(request.isbn());
    edition.setLanguage(request.language());
    edition.setFormat(request.format());
    edition.setPageCount(request.pageCount());
    edition.setPublisher(request.publisher());
    edition.setPrice(request.price());
    edition.setPublicationDate(request.publicationDate());
    edition.setBook(book);
    return edition;
  }

  public BookEditionResponse toResponse(BookEdition edition) {
    return new BookEditionResponse(
        edition.getId(),
        edition.getIsbn(),
        edition.getLanguage(),
        edition.getFormat(),
        edition.getPageCount(),
        edition.getPublisher(),
        edition.getPrice(),
        edition.getPublicationDate(),
        edition.getBook().getId(),
        edition.getBook().getTitle());
  }

  public List<BookEditionResponse> toResponseList(List<BookEdition> editions) {
    if (editions == null) return Collections.emptyList();

    return editions.stream().map(this::toResponse).toList();
  }
}
