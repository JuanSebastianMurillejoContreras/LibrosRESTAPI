package com.libros.librosrestapi.Libro.repository;

import com.libros.librosrestapi.Libro.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepo extends JpaRepository<LibroEntity, Long> {

    boolean existsByIsbn(String isbn);

}
