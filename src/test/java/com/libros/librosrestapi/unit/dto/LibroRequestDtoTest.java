package com.libros.librosrestapi.unit.dto;

import com.libros.librosrestapi.Libro.DTO.input.LibroRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitario para validar restricciones de LibroRequestDTO
 * usando Validator de Jakarta Bean Validation.
 */

class LibroRequestDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenTituloIsBlank_thenValidationFails() {
        var dto = new LibroRequestDTO(1, "", "Autor", "978-3-16-148410-0");

        Set<ConstraintViolation<LibroRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("titulo")
                && v.getMessage().equals("El título no puede estar vacío")));
    }

    @Test
    void whenAutorHasInvalidCharacters_thenValidationFails() {
        var dto = new LibroRequestDTO(1, "Titulo", "Autor123", "978-3-16-148410-0");

        Set<ConstraintViolation<LibroRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("autor")
                && v.getMessage().contains("solo puede contener letras y espacios")));
    }

    @Test
    void whenIsbnIsInvalidFormat_thenValidationFails() {
        var dto = new LibroRequestDTO(1, "Titulo", "Autor", "1234");

        Set<ConstraintViolation<LibroRequestDTO>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn")
                && v.getMessage().contains("ISBN debe tener 13 dígitos")));
    }

    @Test
    void whenAllFieldsAreValid_thenValidationSucceeds() {
        var dto = new LibroRequestDTO(1, "Titulo", "Autor", "978-3-16-148410-0");

        Set<ConstraintViolation<LibroRequestDTO>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }
}
