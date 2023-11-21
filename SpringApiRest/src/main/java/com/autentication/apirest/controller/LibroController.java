package com.autentication.apirest.controller;

import com.autentication.apirest.DTO.LibroDTO;
import com.autentication.apirest.DTO.LibroMapper;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.ILibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/libros")
public class LibroController {
    private Long currentId = 1L; // Comienza desde 1 y aumenta con cada creación
    private ILibroService libroService;
    private IAuthorService authorService;
    private LibroMapper libroMapper = new LibroMapper();

    public LibroController(ILibroService libroService, IAuthorService authorService) {
        this.libroService = libroService;
        this.authorService = authorService;
    }

    //    Devuelve todos los autores
//    El metodo retorna ResponseEntity porque nos da mayor control sobre los Status http que nos da el request
//    Sirve para hacer las pruebas en PostmMan
    @GetMapping
    public ResponseEntity<List<LibroDTO>> getLibros() {
        List<Libro> libros = this.libroService.listLibros();
        List<LibroDTO> libroDTOs = libros.stream()
                .map(LibroMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(libroDTOs, HttpStatus.OK);
    }

    // GET /libros/id
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = this.libroService.searchLibro(id);

        if (libro.isPresent()) {
            return new ResponseEntity<>(LibroMapper.toDTO(libro.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // POST /libros
    @PostMapping
    public ResponseEntity<LibroDTO> createLibro(@RequestBody LibroDTO libro) {
        Long authorId = libro.getAutorId();

        Author author = authorService.searchAuthor(authorId).orElse(null);
        System.out.println("author id "+authorId);
        List<Author> authors = authorService.listAuthores();

        for (int i = 0; i < authors.size(); i++) {
            System.out.println("authorsss id: " + authors.get(i));
        }

        if (author != null) {
            Libro newLibro = libroMapper.toEntity(libro);
            newLibro.setAutor(author);
            this.libroService.createLibro(newLibro);

            return new ResponseEntity<>(LibroMapper.toDTO(newLibro), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{titulo}")
    public ResponseEntity<Long> getLibroIdByName(@PathVariable String titulo) {
        List<Libro> libros = this.libroService.listLibros();

        for (int i = 0; i < libros.size(); i++) {
            System.out.println(libros.get(i).getTitulo());
            if (libros.get(i).getTitulo().equals(titulo)){
                return new ResponseEntity<>(libros.get(i).getId(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> updateLibro(@PathVariable Long id, @RequestBody LibroDTO libro) {
        Libro previous = libroService.searchLibro(id).orElse(null);

        Optional<Author> author = authorService.searchAuthor(libro.getAutorId());

        if (author.isPresent()) {
            if (previous != null) {
                Libro newLibro = libroMapper.toEntity(libro);
                newLibro.setAutor(author.get());
                newLibro.setId(id);

                Libro updateLibro = libroService.editLibro(id, newLibro);

                if (updateLibro != null) {
                    return new ResponseEntity<>(LibroMapper.toDTO(updateLibro), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    // DELETE /autores/{id}: Eliminar un autor.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);

        return (new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}