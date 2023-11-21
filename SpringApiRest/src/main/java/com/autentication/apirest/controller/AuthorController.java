package com.autentication.apirest.controller;

import com.autentication.apirest.DTO.AuthorDTO;
import com.autentication.apirest.DTO.AuthorMapper;
import com.autentication.apirest.DTO.LibroDTO;
import com.autentication.apirest.DTO.LibroMapper;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.ILibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AuthorController {
    private Long currentId = 1L; // Comienza desde 1 y aumenta con cada creación
    private IAuthorService authorService;

    public AuthorController(IAuthorService authorService, ILibroService libroService) {
        this.authorService = authorService;

        AuthorDTO authorDTO1 = new AuthorDTO("Autor 1", "Colombia");
        AuthorDTO authorDTO2 = new AuthorDTO("Autor 2", "Argentina");
        AuthorDTO authorDTO3 = new AuthorDTO("Autor 3", "España");
        AuthorDTO authorDTO4 = new AuthorDTO("Autor 4", "Polonia");
        AuthorDTO authorDTO5 = new AuthorDTO("Autor 5", "Chile");

        saveAuthorInitial(authorDTO1);
        saveAuthorInitial(authorDTO2);
        saveAuthorInitial(authorDTO3);
        saveAuthorInitial(authorDTO4);
        saveAuthorInitial(authorDTO5);
    }

    public void saveAuthorInitial(AuthorDTO authorDTO){
        Author author = AuthorMapper.toEntity(authorDTO);
        createAuthor(author);
    }

    //Devuelve todos los autores
    //El metodo retorna ResponseEntity porque nos da mayor control sobre los Status http que nos da el request
    //Sirve para hacer las pruebas en PostmMan
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorService.listAuthores();
        List<AuthorDTO> authorDTOs = authors.stream()
                .map(AuthorMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }

    // GET /autores/{id}: Obtener detalles de un autor específico.
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = this.authorService.searchAuthor(id);

        if (author.isPresent()) {
            return new ResponseEntity<>(AuthorMapper.toDTO(author.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST /autores: Crear un nuevo autor.
    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody Author autor) {
        System.out.println("entra a crear autor");
        Author newAuthor = this.authorService.createAuthor(autor);

        if (newAuthor != null) {
            System.out.println(AuthorMapper.toDTO(newAuthor).getNacionalidad());
            return new ResponseEntity<>(AuthorMapper.toDTO(newAuthor), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{name}")
    public ResponseEntity<Long> getAuthorIdByName(@PathVariable String name) {
        List<Author> authors = this.authorService.listAuthores();

        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getNombre().equals(name)){
                return new ResponseEntity<>(authors.get(i).getId(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody AuthorDTO autor) {
        Author previous = authorService.searchAuthor(id).orElse(null);

        if (previous != null) {
            Author updatedAuthor = authorService.editAuthor(id, autor);

            if (updatedAuthor != null) {
                return new ResponseEntity<>(AuthorMapper.toDTO(updatedAuthor), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /autores/{id}: Eliminar un autor.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return (new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    //GET /autores/{id}/libros: Listar los libros de un autor específico.
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<LibroDTO>> getLibrosByAuthor(@PathVariable Long id) {
        List<Libro> libros = this.authorService.listLibrosFromAutor(id);
        List<LibroDTO> libroDTOs = libros.stream()
                .map(LibroMapper::toDTO)
                .collect(Collectors.toList());
        if (libros != null) {
            return new ResponseEntity<>(libroDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
