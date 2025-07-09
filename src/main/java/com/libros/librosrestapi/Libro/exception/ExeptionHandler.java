package com.libros.librosrestapi.Libro.exception;

import com.libros.librosrestapi.Libro.DTO.output.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .toList().toString();
        return new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), errors);
    }

    @ExceptionHandler(LibroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoNotificationsFoundException(LibroNotFoundException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(LibroException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleLibroException(LibroException ex) {
        return new ErrorResponse(ex.getCode(), ex.getMessage());
    }



}