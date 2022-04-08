package com.steepcliff.thinkboom.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_FOUND_ROOM(HttpStatus.NOT_FOUND, "404_1","해당 방이 존재하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
