package prog.hei.app.endpoint.rest.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import prog.hei.app.dto.author.request.AuthorRequest;
import prog.hei.app.dto.author.response.AuthorResponse;
import prog.hei.app.service.AuthorService;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping
  public List<AuthorResponse> getAll() {
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  public AuthorResponse getById(@PathVariable UUID id) {
    return authorService.findById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AuthorResponse create(@Valid @RequestBody AuthorRequest dto) {
    return authorService.create(dto);
  }

  @PutMapping("/{id}")
  public AuthorResponse update(@PathVariable UUID id, @Valid @RequestBody AuthorRequest dto) {
    return authorService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable UUID id) {
    authorService.delete(id);
  }
}
