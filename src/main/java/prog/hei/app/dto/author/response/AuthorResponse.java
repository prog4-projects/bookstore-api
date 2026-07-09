package prog.hei.app.dto.author.response;

import java.time.LocalDate;
import prog.hei.app.entity.enums.UserGenderEnum;

public record AuthorResponse(
    String id, String firstName, String lastName, LocalDate birthDate, UserGenderEnum gender) {}
