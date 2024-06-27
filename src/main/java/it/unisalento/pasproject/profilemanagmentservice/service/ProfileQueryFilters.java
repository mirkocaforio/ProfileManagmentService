package it.unisalento.pasproject.profilemanagmentservice.service;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileQueryFilters {
    private String role;
    private String name;
    private String surname;
    private String email;
    private Boolean isEnabled;
    private LocalDateTime registrationDate;
    private String residenceCity;
    private String residenceAddress;
    private String phoneNumber;
    private String fiscalCode;
    private LocalDateTime birthDate;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
}
