package com.libros.librosrestapi.integration.dto;

import com.libros.librosrestapi.Libro.DTO.input.LibroCreateRequestDTO;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LibroCreateRequestDTOValidationTest {

    @Autowired
    private Validator validator;


    @Test
    void tituloNoPuedeEstarVacio() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(1, "", "Autor", "978-3-16-148410-0");

        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("titulo")
                        && v.getMessage().contains("no puede estar vacío")));
    }

    @Test
    void autorSoloPuedeTenerLetras() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(1, "Título", "Autor123", "978-3-16-148410-0");

        var violations = validator.validate(dto);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("autor")
                        && v.getMessage().contains("solo puede contener letras")));
    }
}