package com.autentication.apirest;

import com.autentication.apirest.DTO.LibroDTO;
import com.autentication.apirest.controller.LibroController;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.ILibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LibroServiceTest {

    @Mock
    private ILibroService libroService;

    @Mock
    private IAuthorService authorService;

    @InjectMocks
    private LibroController libroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getLibrosTest() {
        List<Libro> libros = new ArrayList<>();
        //libros.add()

        when(libroService.listLibros()).thenReturn(libros);

        ResponseEntity<List<LibroDTO>> response = libroController.getLibros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(libros.size(), response.getBody().size());
    }

    @Test
    void createLibroTest() {
        LibroDTO libroDTO = new LibroDTO();
        // Configura los detalles del libroDTO

        Libro libro = new Libro();
        // Configura los detalles del libro

        when(authorService.searchAuthor(anyLong())).thenReturn(Optional.of(new Author()));
        when(libroService.createLibro(any(Libro.class))).thenReturn(libro);

        ResponseEntity<LibroDTO> response = libroController.createLibro(libroDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void deleteLibroTest() {
        Long id = 1L;

        doNothing().when(libroService).deleteLibro(anyLong());

        ResponseEntity<Void> response = libroController.deleteLibro(id);

        verify(libroService).deleteLibro(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


}
