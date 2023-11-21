package com.autentication.apirest.DTO;

import com.autentication.apirest.model.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setNombre(author.getNombre());
        authorDTO.setNacionalidad(author.getNacionalidad());
        return authorDTO;
    }

    public static Author toEntity(AuthorDTO dto){
        // tratando de que Author tenga constructor donde ID sea automático, no pasado
        // por parámetro
        Author author = new Author();
        author.setNombre(dto.getNombre());
        author.setNacionalidad(dto.getNacionalidad());
        return author;
    }
}
