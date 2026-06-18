package prog.hei.app.dto.book.response;

import java.util.UUID;
import prog.hei.app.entity.enums.UserGenderEnum;

public record BookAuthorResponse(
    UUID id, String firstName, String lastName, UserGenderEnum gender) {}
