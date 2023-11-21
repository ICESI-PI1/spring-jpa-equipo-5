package com.autentication.apirest.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO implements Serializable {
    private String titulo;
    private Date fechaPublicacion;
    private String autorNombre;
}
