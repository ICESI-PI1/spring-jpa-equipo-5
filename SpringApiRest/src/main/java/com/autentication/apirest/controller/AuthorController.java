package com.autentication.apirest.controller;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.ILibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autores")
public class AuthorController {
    private Long currentId = 1L; // Comienza desde 1 y aumenta con cada creación
    private IAuthorService authorService;

    public AuthorController(IAuthorService authorService, ILibroService libroService){
        this.authorService = authorService;
    }

    //Devuelve todos los autores
    //El metodo retorna ResponseEntity porque nos da mayor control sobre los Status http que nos da el request
    //Sirve para hacer las pruebas en PostmMan
    @GetMapping
    public ResponseEntity<Iterable<Author>> getAllAuthors() {
        Iterable<Author> authors = authorService.listAuthores();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    // GET /autores/{id}: Obtener detalles de un autor específico.
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = this.authorService.searchAuthor(id);

        if (author.isPresent()){
            return new ResponseEntity<>(author.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    // POST /autores: Crear un nuevo autor.
    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author autor) {
        autor.setId(currentId);
        currentId++;

        Author newAuthor = this.authorService.createAuthor(autor);

        if (newAuthor != null){
            return new ResponseEntity<>(newAuthor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author autor) {
        Author previous = authorService.searchAuthor(id).orElse(null);

        if (previous != null) {
            if (previous.getId().equals(autor.getId())){
                Author updatedAuthor = authorService.editAuthor(id, autor);

                if (updatedAuthor != null){
                    return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /autores/{id}: Eliminar un autor.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
         authorService.deleteAuthor(id);

        return  (new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    //GET /autores/{id}/libros: Listar los libros de un autor específico.
    @GetMapping("/{id}/libros")
    public ResponseEntity<List<Libro>> getLibrosByAuthor(@PathVariable Long id) {
        List<Libro> libros = this.authorService.listLibrosFromAutor(id);

        if (libros != null){
            return new ResponseEntity<>(libros, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
