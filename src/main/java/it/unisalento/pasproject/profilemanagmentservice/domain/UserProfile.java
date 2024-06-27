package it.unisalento.pasproject.profilemanagmentservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfile extends GenericProfile {
    private String residenceCity;
    private String residenceAddress;
    private String phoneNumber;
    private String fiscalCode;
    private LocalDateTime birthDate;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardCvv;

    public UserProfile() {}
}
