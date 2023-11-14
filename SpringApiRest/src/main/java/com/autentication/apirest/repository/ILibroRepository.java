package com.autentication.apirest.repository;

import com.autentication.apirest.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro,Long> {

}
