package prog.hei.app.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import prog.hei.app.dto.library.request.LibraryRequest;
import prog.hei.app.dto.library.response.LibraryBookResponse;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.entity.Library;
import prog.hei.app.entity.LibraryBook;

@Component
public class LibraryMapper {
  public LibraryResponse toResponse(Library library) {
    return new LibraryResponse(
        library.getId(),
        library.getName(),
        library.getAddress(),
        library.getPhoneNumber(),
        toLibraryBookResponses(library.getLibraryBooks()));
  }

  public Library toEntity(LibraryRequest request) {
    Library library = new Library();

    library.setName(request.name());
    library.setAddress(request.address());
    library.setPhoneNumber(request.phoneNumber());

    return library;
  }

  public void updateEntity(Library library, LibraryRequest request) {
    library.setName(request.name());
    library.setAddress(request.address());
    library.setPhoneNumber(request.phoneNumber());
  }

  public LibraryBookResponse toLibraryBookResponse(LibraryBook libraryBook) {
    return new LibraryBookResponse(
        libraryBook.getBookEdition().getId(),
        libraryBook.getBookEdition().getBook().getTitle(),
        libraryBook.getBookEdition().getBook().getDescription(),
        libraryBook.getBookEdition().getBook().getGender(),
        libraryBook.getBookEdition().getIsbn(),
        libraryBook.getBookEdition().getLanguage(),
        libraryBook.getBookEdition().getFormat(),
        libraryBook.getBookEdition().getPageCount(),
        libraryBook.getBookEdition().getPublisher(),
        libraryBook.getBookEdition().getPrice(),
        libraryBook.getBookEdition().getPublicationDate());
  }

  private List<LibraryBookResponse> toLibraryBookResponses(List<LibraryBook> libraryBooks) {
    if (libraryBooks == null) return new ArrayList<>();
    return libraryBooks.stream().map(this::toLibraryBookResponse).toList();
  }
}
