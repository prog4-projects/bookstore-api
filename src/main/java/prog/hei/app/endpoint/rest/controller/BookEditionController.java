package prog.hei.app.endpoint.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prog.hei.app.entity.BookEdition;
import prog.hei.app.service.BookEditionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book-editions")
@RequiredArgsConstructor
public class BookEditionController {

    private final BookEditionService service;

    @PostMapping
    public BookEdition create(@RequestBody BookEdition bookEdition) {
        return service.create(bookEdition);
    }

    @GetMapping
    public List<BookEdition> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookEdition getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public BookEdition update(@PathVariable UUID id,
                              @RequestBody BookEdition bookEdition) {
        return service.update(id, bookEdition);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
