package com.autentication.apirest.repository;

import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.services.ILibroService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAuthorRepository extends JpaRepository<Author, Long> {

}