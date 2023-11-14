package com.autentication.apirest.model;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="Libro")
@Table(name = "LIBROS")
public class Libro {
    @Id
    @SequenceGenerator(
            name="libro_sequence",
            sequenceName = "libro_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = SEQUENCE, generator = "libro_sequence")
    private Long id;

    @Column
    private String titulo;
    @Column
    private Date fechaPublicacion;
    @Column
    private Long autorId;

    public Libro(Long id, String titulo, Date fechaPublicacion, Long autorId) {
        this.id = id;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.autorId = autorId;
    }

    public Libro(Libro libro) {
        this(libro.getId(), libro.getTitulo(), libro.getFechaPublicacion(), libro.getAutorId());
    }

    public Libro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }
}
