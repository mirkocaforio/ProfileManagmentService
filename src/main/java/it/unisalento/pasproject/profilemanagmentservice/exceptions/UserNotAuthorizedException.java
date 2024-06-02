package it.unisalento.pasproject.profilemanagmentservice.exceptions;

import it.unisalento.pasproject.profilemanagmentservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAuthorizedException extends CustomErrorException {

    public UserNotAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
