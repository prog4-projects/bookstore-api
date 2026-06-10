package prog.hei.app.dto.library.response;

import java.util.List;
import java.util.UUID;

public record LibraryResponse(
    UUID id,
    String name,
    String address,
    String phoneNumber,
    List<LibraryBookResponse> libraryBooks) {}
