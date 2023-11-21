package com.autentication.apirest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.autentication.apirest.DTO.AuthorDTO;
import com.autentication.apirest.DTO.AuthorMapper;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.services.ILibroService;
import com.autentication.apirest.services.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private IAuthorRepository authorRepository;

    @Mock
    private ILibroService libroService;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private Libro libro;


    @BeforeEach
    void setUp() {
        author = new Author(1L, "Garcia", "Colombiano");
        libro = new Libro();
        libro.setAutor(author);
    }

    @Test
    void createAuthorTest() {
        AuthorDTO authorDTO = AuthorMapper.toDTO(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author result = authorService.createAuthor(AuthorMapper.toEntity(authorDTO));
        assertNotNull(result);
        assertEquals("Garcia", result.getNombre());
        System.out.println("createAuthorTest - Autor creado: " + result.getNombre());
    }



    @Test
    void searchAuthorTest() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> found = authorService.searchAuthor(1L);
        assertTrue(found.isPresent());
        assertEquals("Garcia", found.get().getNombre());
    }

    @Test
    void deleteAuthorTest() {
        doNothing().when(authorRepository).deleteById(anyLong());
        authorService.deleteAuthor(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void listAuthoresTest() {
        List<Author> authors = Arrays.asList(author);
        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> result = authorService.listAuthores();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Garcia", result.get(0).getNombre());
    }

    @Test
    void editAuthorTest() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        AuthorDTO updateDTO = new AuthorDTO();
        updateDTO.setNombre("Marquez");
        updateDTO.setNacionalidad("Mexicano");
        Author updatedAuthor = authorService.editAuthor(1L, updateDTO);
        assertNotNull(updatedAuthor);
        assertEquals("Marquez", updatedAuthor.getNombre());
        assertEquals("Mexicano", updatedAuthor.getNacionalidad());
    }

    @Test
    void listLibrosFromAutorTest() {
        List<Libro> libros = new ArrayList<>();
        libros.add(libro);

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        when(libroService.listLibros()).thenReturn(libros);

        List<Libro> result = authorService.listLibrosFromAutor(1L);

        assertNotNull(result, "La lista de libros no es nula");
        assertEquals(1, result.size(), "Tamaño lista = 1");
        assertEquals(author, result.get(0).getAutor(), "Autor corresponde");

        verify(authorRepository).findById(anyLong());
        verify(libroService).listLibros();
    }



}