package com.autentication.apirest;

import com.autentication.apirest.DTO.AuthorDTO;
import com.autentication.apirest.controller.AuthorController;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.services.IAuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceTest {

    @Mock
    private IAuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllAuthorsTest() {
        List<Author> authors = new ArrayList<>();
        // Agregar autores

        when(authorService.listAuthores()).thenReturn(authors);

        ResponseEntity<List<AuthorDTO>> response = authorController.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authors.size(), response.getBody().size());
    }
    @Test
    void createAuthorTest() {
        AuthorDTO authorDTO = new AuthorDTO("Nombre", "Nacionalidad");
        Author author = new Author(1L, "Nombre", "Nacionalidad");

        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        ResponseEntity<AuthorDTO> response = authorController.createAuthor(author);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(authorDTO.getNombre(), response.getBody().getNombre());
    }
    @Test
    void deleteAuthorTest() {
        Long id = 1L;

        doNothing().when(authorService).deleteAuthor(anyLong());

        ResponseEntity<Void> response = authorController.deleteAuthor(id);

        verify(authorService).deleteAuthor(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void updateAuthorTest() {
        Long id = 1L;
        AuthorDTO authorDTO = new AuthorDTO("Nombre Actualizado", "Nacionalidad");
        Author updatedAuthor = new Author(id, "Nombre Actualizado", "Nacionalidad");

        when(authorService.editAuthor(eq(id), any(AuthorDTO.class))).thenReturn(updatedAuthor);

        ResponseEntity<AuthorDTO> response = authorController.updateAuthor(id, authorDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(authorDTO.getNombre(), response.getBody().getNombre());
    }


}

