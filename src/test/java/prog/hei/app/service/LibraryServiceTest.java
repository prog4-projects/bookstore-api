package prog.hei.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import prog.hei.app.dto.library.request.LibraryRequest;
import prog.hei.app.dto.library.response.LibraryResponse;
import prog.hei.app.entity.Library;
import prog.hei.app.exception.LibraryNotFoundException;
import prog.hei.app.mapper.LibraryMapper;
import prog.hei.app.repository.LibraryRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private LibraryMapper libraryMapper;

    @InjectMocks
    private LibraryService libraryService;

    private UUID id;
    private Library library;
    private LibraryRequest request;
    private LibraryResponse response;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        library = new Library();
        library.setId(id);
        library.setName("Test Library");
        library.setAddress("Test Address");
        library.setPhoneNumber("01234567");
        library.setLibraryBooks(Collections.emptyList());

        request = new LibraryRequest("Test Library", "Test Address", "01234567");

        response = new LibraryResponse(id, "Test Library", "Test Address", "01234567", Collections.emptyList());
    }

    @Test
    void getAll_shouldReturnMappedResponses_whenLibrariesExist() {
        List<Library> libraries = Arrays.asList(library);
        when(libraryRepository.findAll()).thenReturn(libraries);
        when(libraryMapper.toResponse(library)).thenReturn(response);

        List<LibraryResponse> results = libraryService.getAll();

        assertThat(results).containsExactly(response);
        verify(libraryRepository).findAll();
    }

    @Test
    void getById_shouldReturnMappedResponse_whenLibraryExists() {
        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
        when(libraryMapper.toResponse(library)).thenReturn(response);

        LibraryResponse result = libraryService.getById(id);

        assertThat(result).isEqualTo(response);
        verify(libraryRepository).findById(id);
    }

    @Test
    void getById_shouldThrowException_whenLibraryDoesNotExist() {
        when(libraryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.getById(id)).isInstanceOf(LibraryNotFoundException.class);
    }

    @Test
    void create_shouldReturnMappedResponse_whenSaved() {
        when(libraryMapper.toEntity(request)).thenReturn(library);
        when(libraryRepository.save(library)).thenReturn(library);
        when(libraryMapper.toResponse(library)).thenReturn(response);

        LibraryResponse result = libraryService.create(request);

        assertThat(result).isEqualTo(response);
        verify(libraryMapper).toEntity(request);
        verify(libraryRepository).save(library);
    }

    @Test
    void update_shouldReturnMappedResponse_whenLibraryExists() {
        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
        when(libraryMapper.toResponse(library)).thenReturn(response);

        LibraryResponse result = libraryService.update(id, request);

        assertThat(result).isEqualTo(response);
        assertThat(library.getName()).isEqualTo(request.name());
        assertThat(library.getAddress()).isEqualTo(request.address());
        assertThat(library.getPhoneNumber()).isEqualTo(request.phoneNumber());
    }

    @Test
    void update_shouldThrowException_whenLibraryDoesNotExist() {
        when(libraryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.update(id, request)).isInstanceOf(LibraryNotFoundException.class);
    }

    @Test
    void delete_shouldDeleteLibrary_whenLibraryExists() {
        when(libraryRepository.findById(id)).thenReturn(Optional.of(library));
        libraryService.delete(id);
        verify(libraryRepository).delete(library);
    }

    @Test
    void delete_shouldThrowException_whenLibraryDoesNotExist() {
        when(libraryRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> libraryService.delete(id)).isInstanceOf(LibraryNotFoundException.class);
    }
}

