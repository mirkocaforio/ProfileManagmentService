package it.unisalento.pasproject.profilemanagmentservice.exceptions;

import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends CustomErrorException {
    public ProfileNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
