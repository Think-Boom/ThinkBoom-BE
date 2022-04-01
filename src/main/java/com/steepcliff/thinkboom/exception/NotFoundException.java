package com.steepcliff.thinkboom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException{

    private String errorMessage;

    private HttpStatus httpStatus;

    public NotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
