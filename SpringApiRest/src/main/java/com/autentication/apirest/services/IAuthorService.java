package com.autentication.apirest.services;


import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {

    public Author createAuthor(Author Author);
    public void deleteAuthor(Long id);
    public Optional<Author> searchAuthor(Long id);
    public List<Author> listAuthores();
    public Author editAuthor(Long id, Author Author);
    public List<Libro> listLibrosFromAutor(Long id);
}