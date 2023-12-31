package com.autentication.apirest.model;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name="Libro")
@Table(name = "LIBROS")
@Data
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

    // many to one, necesitamos instancia de ese ONE
    @ManyToOne
    private Author autor;

    public Libro(Long id, String titulo, Date fechaPublicacion, Author autor) {
        this.id = id;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.autor = autor;
    }

    public Libro(Libro libro) {
        this(libro.getId(), libro.getTitulo(), libro.getFechaPublicacion(), libro.getAutor());
    }

    public Libro() {

    }
    public Libro(String titulo, Date fecha, Author aut){
        this.titulo = titulo;
        fechaPublicacion=fecha;
        autor=aut;
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

    public Author getAutor() {
        return autor;
    }

    public void setAutor(Author autor) {
        this.autor = autor;
    }
}
