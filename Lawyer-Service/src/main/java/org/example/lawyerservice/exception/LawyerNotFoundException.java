package org.example.lawyerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Lawyer in Repository")
public class LawyerNotFoundException extends RuntimeException {

    public LawyerNotFoundException(String message) {
        super(message);
    }
}
