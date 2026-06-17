package prog.hei.app.mapper;

import org.springframework.stereotype.Component;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse;
import prog.hei.app.entity.BookEdition;

@Component
public class BookEditionMapper {

    public BookEdition toEntity(BookEditionRequest request) {
        BookEdition entity = new BookEdition();
        entity.setIsbn(request.getIsbn());
        entity.setLanguage(request.getLanguage());
        entity.setFormat(request.getFormat());
        entity.setPageCount(request.getPageCount());
        entity.setPublisher(request.getPublisher());
        entity.setPrice(request.getPrice());
        entity.setPublicationDate(request.getPublicationDate());
        entity.setStockQuantity(request.getStockQuantity());
        return entity;
    }

    public BookEditionResponse toResponse(BookEdition entity) {
        return BookEditionResponse.builder()
                .id(entity.getId())
                .isbn(entity.getIsbn())
                .language(entity.getLanguage())
                .format(entity.getFormat())
                .pageCount(entity.getPageCount())
                .publisher(entity.getPublisher())
                .price(entity.getPrice())
                .publicationDate(entity.getPublicationDate())
                .stockQuantity(entity.getStockQuantity())
                .build();
    }
}
