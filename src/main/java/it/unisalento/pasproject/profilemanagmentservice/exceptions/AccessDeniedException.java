package it.unisalento.pasproject.profilemanagmentservice.exceptions;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CustomErrorException{
    public AccessDeniedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
