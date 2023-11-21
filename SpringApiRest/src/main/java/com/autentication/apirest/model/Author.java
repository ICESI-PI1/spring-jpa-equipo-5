package com.autentication.apirest.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity(name = "Author")
@Table(name = "AUTORES")
@Data
public class Author {
    @Id
    @SequenceGenerator(
            name="author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = SEQUENCE, generator = "author_sequence")
    private Long id;
    @Column
    private String nombre;
    @Column
    private String nacionalidad;

    public Author(Long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }
    public Author(Author author){
        this(author.getId(),author.getNombre(), author.getNacionalidad());
    }
    public Author(){

    }



    public Author(String nombre, String nacionalidad){
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}

