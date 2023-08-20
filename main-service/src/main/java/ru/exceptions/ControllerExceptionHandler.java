package ru.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException conflictException) {
        return new ResponseEntity<>(conflictException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handlePSQLException(PSQLException psqlException) {
        return new ResponseEntity<>(psqlException, HttpStatus.CONFLICT);
    }
}
