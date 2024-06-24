package org.example.challengestationspaths.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BadRequestException extends CustomHttpException {

    @Serial
    private static final long serialVersionUID = -4366929906549897306L;

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
