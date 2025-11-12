package org.example.lawclientservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such LawCase in Repository")
public class LawClientNotFoundException extends RuntimeException {

    public LawClientNotFoundException(String message) {
        super(message);
    }
}
