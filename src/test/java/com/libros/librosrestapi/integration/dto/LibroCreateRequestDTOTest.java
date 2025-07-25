package com.libros.librosrestapi.integration.dto;

import com.libros.librosrestapi.libro.DTO.input.LibroCreateRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LibroCreateRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDTO() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(
                "Un título",
                "Gabriel García Márquez",
                "978-3-16-148410-0",
                "Editorial X"
        );

        Set<ConstraintViolation<LibroCreateRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "No debería haber violaciones para un DTO válido");
    }

    @Test
    void testTituloBlank() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(
                "   ",
                "Autor válido",
                "978-3-16-148410-0",
                "Editorial X"
        );

        Set<ConstraintViolation<LibroCreateRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")));
    }

    @Test
    void testAutorConNumeros() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(
                "Un título",
                "Gabriel García Márquez123",
                "978-3-16-148410-0",
                "Editorial X"
        );

        Set<ConstraintViolation<LibroCreateRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("autor")));
    }

    @Test
    void testIsbnInvalido() {
        LibroCreateRequestDTO dto = new LibroCreateRequestDTO(
                "Un título",
                "Gabriel García Márquez",
                "ABC123",
                "Editorial X"
        );

        Set<ConstraintViolation<LibroCreateRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn")));
    }
}