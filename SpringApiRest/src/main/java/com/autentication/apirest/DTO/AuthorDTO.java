package com.autentication.apirest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO implements Serializable {
    private String nombre;
    private String nacionalidad;
}
