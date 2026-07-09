package prog.hei.app.dto.author.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import prog.hei.app.entity.enums.UserGenderEnum;

public record AuthorRequest(
    @NotBlank(message = "First name is required")
        @Size(min = 2, max = 255, message = "First name must be between 2 and 255 characters")
        String firstName,
    @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
        String lastName,
    @NotNull(message = "Birth date is required") @Past(message = "Birth date must be in the past")
        LocalDate birthDate,
    @NotNull(message = "Gender is required") UserGenderEnum gender) {}
