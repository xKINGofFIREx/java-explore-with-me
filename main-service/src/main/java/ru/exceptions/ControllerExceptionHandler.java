package ru.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(notFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException conflictException) {
        return new ResponseEntity<>(conflictException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handlePSQLException(PSQLException psqlException) {
        return new ResponseEntity<>(psqlException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handlePSQLException(ValidationException validationException) {
        return new ResponseEntity<>(validationException, HttpStatus.BAD_REQUEST);
    }
}
