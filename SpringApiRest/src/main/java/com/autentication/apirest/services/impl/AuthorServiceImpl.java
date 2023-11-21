package com.autentication.apirest.services.impl;

import com.autentication.apirest.DTO.AuthorDTO;
import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.ILibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements IAuthorService {

    IAuthorRepository authorRepository;
    private ILibroService libroService;

    @Autowired
    public AuthorServiceImpl(IAuthorRepository authorRepository, ILibroService libroService) {
        this.authorRepository = authorRepository;
        this.libroService = libroService;
    }

    @Override
    public Author createAuthor(Author Author) {
        return authorRepository.save(Author);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Optional<Author> searchAuthor(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> listAuthores() {
        return authorRepository.findAll();
    }

    @Override
    public Author editAuthor(Long id, AuthorDTO authorUpdate) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setNacionalidad(authorUpdate.getNacionalidad());
            author.setNombre(authorUpdate.getNombre());
            return authorRepository.save(author);
        } else {
            throw new EntityNotFoundException("author con id " + id + " no existe");
        }
    }

    @Override
    public List<Libro> listLibrosFromAutor(Long id) {
        List<Libro> librosFromAutor = new ArrayList<>();

        if (searchAuthor(id).isPresent()) {
            for (Libro l :
                    libroService.listLibros()) {
                if (l.getAutor().getId().equals(id)) {
                    librosFromAutor.add(l);
                }
            }
        } else {
            return null;
        }
        return librosFromAutor;
    }
}
