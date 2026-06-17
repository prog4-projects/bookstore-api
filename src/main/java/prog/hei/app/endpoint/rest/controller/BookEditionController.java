package prog.hei.app.endpoint.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prog.hei.app.dto.bookEdition.request.BookEditionRequest;
import prog.hei.app.dto.bookEdition.response.BookEditionResponse  ;
import prog.hei.app.mapper.BookEditionMapper;
import prog.hei.app.service.BookEditionService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book-editions")
@RequiredArgsConstructor
public class BookEditionController {

    private final BookEditionService service;
    private final BookEditionMapper mapper;

    @PostMapping
    public BookEditionResponse create(@RequestBody BookEditionRequest request) {
        return mapper.toResponse(
                service.create(mapper.toEntity(request))
        );
    }

    @GetMapping
    public List<BookEditionResponse> getAll() {
        return service.getAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookEditionResponse getById(@PathVariable UUID id) {
        return mapper.toResponse(service.getById(id));
    }

    @PutMapping("/{id}")
    public BookEditionResponse update(@PathVariable UUID id,
                                      @RequestBody BookEditionRequest request) {
        return mapper.toResponse(
                service.update(id, mapper.toEntity(request))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
