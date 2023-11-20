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
    public LibroController(ILibroService libroService, IAuthorService authorService){
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

        if (libro.isPresent()){
            return new ResponseEntity<>(LibroMapper.toDTO(libro.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    // POST /libros
    @PostMapping
    public ResponseEntity<LibroDTO> createLibro(@RequestBody Libro libro) {
        Long authorId = libro.getAutorId();

        Author author = authorService.searchAuthor(authorId).orElse(null);

        if (author != null){
            libro.setId(currentId);
            currentId++;

            Libro newLibro = this.libroService.createLibro(libro);

            if (newLibro != null){
                return new ResponseEntity<>(LibroMapper.toDTO(newLibro), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Libro previous = libroService.searchLibro(id).orElse(null);

        if (previous != null) {
            if (previous.getId().equals(libro.getId()) &&
                    previous.getAutorId().equals(libro.getAutorId())){
                Libro updateLibro = libroService.editLibro(id, libro);

                if (updateLibro != null){
                    return new ResponseEntity<>(LibroMapper.toDTO(updateLibro), HttpStatus.OK);
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
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
         libroService.deleteLibro(id);

        return  (new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}