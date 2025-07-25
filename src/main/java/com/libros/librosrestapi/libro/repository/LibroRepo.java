package com.libros.librosrestapi.libro.repository;

import com.libros.librosrestapi.libro.entity.LibroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepo extends JpaRepository<LibroEntity, Long> {

    boolean existsByIsbn(String isbn);

}
