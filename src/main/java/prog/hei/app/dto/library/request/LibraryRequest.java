package prog.hei.app.dto.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record LibraryRequest(
        @NotBlank String name,
        @NotBlank String address,
        @Size(min = 8, max = 15)
        @Pattern(regexp = "^\\d{8,15}$")
        String phoneNumber
) {
}
