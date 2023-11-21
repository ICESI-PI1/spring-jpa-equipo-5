package com.autentication.apirest.DTO;

import com.autentication.apirest.model.Author;
import com.autentication.apirest.model.Libro;
import com.autentication.apirest.repository.IAuthorRepository;

public class LibroMapper {

    public static LibroDTO toDTO(Libro libro){
        LibroDTO dto = new LibroDTO();

        // debemos hacer mapping de Autor a AutorDTO con el mapper de DTO
        dto.setAutor(libro.getAutor());
        dto.setTitulo(libro.getTitulo());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        return dto;
    }

    public static Libro toEntity(LibroDTO dto){
        // Necesitamos que Libro cree automáticamente su ID y no sea pasado por parámetro
        Libro libro = new Libro();
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setTitulo(dto.getTitulo());
        // Debemos pasar de AutorDTO a Autor con el mapper, nuevamente
        libro.setAutor(dto.getAutor());
        return libro;
    }
}
