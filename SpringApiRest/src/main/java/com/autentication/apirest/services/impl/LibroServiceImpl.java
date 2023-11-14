package com.autentication.apirest.services.impl;

import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.repository.ILibroRepository;
import com.autentication.apirest.services.ILibroService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
@Service
public class LibroServiceImpl implements ILibroService {
    private ILibroRepository iRepository;

    @Autowired
    public LibroServiceImpl(ILibroRepository libroRepository) {
        this.iRepository = libroRepository;
    }

    @Override
    public Libro createLibro(Libro libro) {
        return iRepository.save(libro);
    }


    public void deleteLibro(Long id) {
         iRepository.deleteById(id);
    }

    @Override
    public Optional<Libro> searchLibro(Long id) {
        return iRepository.findById(id);
    }

    @Override
    public List<Libro> listLibros() {
        return iRepository.findAll();
    }

    @Override
    public Libro editLibro(Long id, Libro libroUpdate) {
        Optional<Libro> existingLibro = iRepository.findById(id);
        if (existingLibro.isPresent()) {
            Libro libro = existingLibro.get();
            libro.setTitulo(libroUpdate.getTitulo());
            libro.setAutorId(libroUpdate.getAutorId());
            libro.setFechaPublicacion(libroUpdate.getFechaPublicacion());
            return iRepository.save(libro);
        } else {
            throw new EntityNotFoundException("Libro con id " + id + " no existe");
        }
    }
}
