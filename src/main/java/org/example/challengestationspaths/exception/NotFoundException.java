package org.example.challengestationspaths.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NotFoundException extends CustomHttpException {

    @Serial
    private static final long serialVersionUID = 2080791908876534618L;

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
