package prog.hei.app.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import prog.hei.app.entity.enums.BookGenderEnum;

public record BookRequest(
    @NotBlank(message = "Title is required")
        @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
        String title,
    @Size(max = 2000, message = "Description must not exceed 2000 characters") String description,
    @NotNull(message = "Gender is required") BookGenderEnum gender) {}
