package prog.hei.app.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.BookEdition;

@Component
public class BookEditionMapper {
  public List<BookEditionResponse> toBookEditionResponse(List<BookEdition> bookEditions) {
    return bookEditions.stream()
        .map(
            bookEdition ->
                new BookEditionResponse(
                    bookEdition.getId(),
                    bookEdition.getIsbn(),
                    bookEdition.getLanguage(),
                    bookEdition.getFormat(),
                    bookEdition.getPageCount(),
                    bookEdition.getPublisher(),
                    bookEdition.getPrice(),
                    bookEdition.getPublicationDate()))
        .toList();
  }
}
