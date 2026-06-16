package prog.hei.app.dto.library.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LibraryRequest(
    @NotBlank String name,
    @NotBlank String address,
    @NotBlank @Size(min = 8, max = 15) @Pattern(regexp = "^\\d{8,15}$") String phoneNumber) {}
