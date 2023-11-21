package com.autentication.apirest.services;

import com.autentication.apirest.DTO.LibroDTO;
import com.autentication.apirest.model.Libro;

import java.util.List;
import java.util.Optional;

public interface ILibroService {
    public Libro createLibro(Libro libro);
    public void deleteLibro(Long id);
    public Optional<Libro> searchLibro(Long id);
    public List<Libro> listLibros();
    public Libro editLibro(Long id, Libro libro);
}
