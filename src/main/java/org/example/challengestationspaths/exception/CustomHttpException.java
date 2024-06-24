package org.example.challengestationspaths.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public abstract class CustomHttpException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6412307403986177552L;

    private HttpStatus status;

    public CustomHttpException(HttpStatus status) {
        this.status = status;
    }

    public CustomHttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
