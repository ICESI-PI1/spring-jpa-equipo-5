package com.autentication.apirest.DTO;

import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;
import com.autentication.apirest.services.IAuthorService;
import com.autentication.apirest.services.impl.AuthorServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class LibroMapper {
    private IAuthorService iAuthorService;

    public static LibroDTO toDTO(Libro libro){
        LibroDTO dto = new LibroDTO();

        // debemos hacer mapping de Autor a AutorDTO con el mapper de DTO
        dto.setAutorNombre(libro.getAutor().getNombre());
        dto.setTitulo(libro.getTitulo());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        return dto;
    }

    public Libro toEntity(LibroDTO dto){
        // Necesitamos que Libro cree automáticamente su ID y no sea pasado por parámetro
        Libro libro = new Libro();
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setTitulo(dto.getTitulo());
        // Debemos pasar de AutorDTO a Autor con el mapper, nuevamente
        //List<Author> authors = iAuthorService.listAuthores();

        //for (int i = 0; i < authors.size(); i++) {
        //    if (authors.get(i).getId().equals(dto.getAutorId())){
                libro.setAutor(null);

        //        return libro;
        //    }
        //}
        return libro;

        //return null;
    }
}
