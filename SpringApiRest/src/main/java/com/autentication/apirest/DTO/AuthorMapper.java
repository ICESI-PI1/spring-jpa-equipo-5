package com.autentication.apirest.DTO;

import com.autentication.apirest.model.Author;

public class AuthorMapper {

    public static AuthorDTO toDTO(Author author){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setNombre(author.getNombre());
        authorDTO.setNacionalidad(authorDTO.getNacionalidad());
        return authorDTO;
    }

    public static Author toEntity(AuthorDTO dto){
        Author author = new Author();
        author.setNombre(dto.getNombre());
        author.setId(dto.getId());
        author.setNacionalidad(dto.getNacionalidad());
        return author;
    }
}
