package it.unisalento.pasproject.profilemanagmentservice.exceptions;

import it.unisalento.pasproject.profilemanagmentservice.exceptions.global.CustomErrorException;
import org.springframework.http.HttpStatus;

public class InvalidProfileTypeException extends CustomErrorException {
    public InvalidProfileTypeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
