package com.libros.librosrestapi.Libro.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_libro")
public class LibroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String autor;
    @Column(unique = true, nullable = false)
    private String isbn;

}
