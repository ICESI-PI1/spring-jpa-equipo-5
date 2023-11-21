package test;

import com.autentication.apirest.model.Libro;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.repository.ILibroRepository;
import com.autentication.apirest.services.impl.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibroServiceTest {

    @Mock
    private ILibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    private Libro libro1, libro2;

    @BeforeEach
    public void setup1() {
        // Suponiendo que la relación con Author es necesaria
        Author author1 = new Author(1L, "Autor 1", "Nacionalidad 1");
        Author author2 = new Author(2L, "Autor 2", "Nacionalidad 2");

        libro1 = new Libro(1L, "Libro 1", new Date(), author1);
        libro2 = new Libro(2L, "Libro 2", new Date(), author2);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro1));
        when(libroRepository.findById(2L)).thenReturn(Optional.of(libro2));
    }

    @Test
    public void createLibroTest() {
        when(libroRepository.save(any(Libro.class))).thenReturn(libro1);
        Libro created = libroService.createLibro(libro1);
        assertNotNull(created);
        assertEquals("Libro 1", created.getTitulo());
    }

    @Test
    public void searchLibroTest() {
        Optional<Libro> found = libroService.searchLibro(1L);
        assertTrue(found.isPresent());
        assertEquals("Libro 1", found.get().getTitulo());
    }

    @Test
    public void editLibroTest() {
        Libro updatedLibro = new Libro(1L, "Libro 1 Editado", new Date(), libro1.getAutor());
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro1));
        when(libroRepository.save(any(Libro.class))).thenReturn(updatedLibro);

        Libro result = libroService.editLibro(1L, updatedLibro);
        assertNotNull(result);
        assertEquals("Libro 1 Editado", result.getTitulo());
    }

    @Test
    public void deleteLibroTest() {
        doNothing().when(libroRepository).deleteById(1L);
        libroService.deleteLibro(1L);
        verify(libroRepository, times(1)).deleteById(1L);
    }


}
