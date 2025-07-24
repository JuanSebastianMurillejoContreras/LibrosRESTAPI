package com.libros.librosrestapi.unit.dto;


import com.libros.librosrestapi.Libro.DTO.input.LibroUpdateRequestDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LibroUpdateRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTitulo() {
        LibroUpdateRequestDTO dto = new LibroUpdateRequestDTO("Un título válido");

        Set<ConstraintViolation<LibroUpdateRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "No debería haber violaciones cuando el título es válido");
    }

    @Test
    void testTituloBlank() {
        LibroUpdateRequestDTO dto = new LibroUpdateRequestDTO("");

        Set<ConstraintViolation<LibroUpdateRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debe haber violaciones cuando el título está vacío");
        ConstraintViolation<LibroUpdateRequestDTO> violation = violations.iterator().next();
        assertEquals("El título no puede estar vacío", violation.getMessage());
    }

    @Test
    void testTituloNull() {
        LibroUpdateRequestDTO dto = new LibroUpdateRequestDTO(null);

        Set<ConstraintViolation<LibroUpdateRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Debe haber violaciones cuando el título es null");
        ConstraintViolation<LibroUpdateRequestDTO> violation = violations.iterator().next();
        assertEquals("El título no puede estar vacío", violation.getMessage());
    }

    @Test
    void testEqualsAndToString() {
        LibroUpdateRequestDTO dto1 = new LibroUpdateRequestDTO("Título");
        LibroUpdateRequestDTO dto2 = new LibroUpdateRequestDTO("Título");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());

        String toString = dto1.toString();
        assertTrue(toString.contains("Título"));
    }
}