package com.steepcliff.thinkboom.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.steepcliff.thinkboom.exception.ErrorCode.NOT_FOUND_ROOM;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException e) {

        NotFoundException notFoundException = new NotFoundException();
        notFoundException.setErrorMessage(e.getErrorMessage());
        notFoundException.setHttpStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity(notFoundException, HttpStatus.BAD_REQUEST);
    }


}
