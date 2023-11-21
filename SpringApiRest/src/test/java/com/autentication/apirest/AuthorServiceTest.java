package com.autentication.apirest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.autentication.apirest.DTO.AuthorDTO;
import com.autentication.apirest.DTO.AuthorMapper;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.services.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private IAuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author(1L, "Garcia", "Colombiano");
    }

    @Test
    void createAuthorTest() {
        AuthorDTO authorDTO = AuthorMapper.toDTO(author);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        Author result = authorService.createAuthor(authorDTO);
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


}
