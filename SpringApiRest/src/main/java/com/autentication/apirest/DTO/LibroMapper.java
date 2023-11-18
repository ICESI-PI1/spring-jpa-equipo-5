package com.autentication.apirest.DTO;

import com.autentication.apirest.model.Libro;

public class LibroMapper {

    public static LibroDTO toDTO(Libro libro){
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setAutorId(libro.getAutorId());
        dto.setTitulo(libro.getTitulo());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        return dto;
    }

    public static Libro toEntity(LibroDTO dto){
        Libro libro = new Libro();
        libro.setFechaPublicacion(dto.getFechaPublicacion());
        libro.setId(dto.getId());
        libro.setTitulo(dto.getTitulo());
        libro.setAutorId(dto.getAutorId());
        return libro;
    }
}
