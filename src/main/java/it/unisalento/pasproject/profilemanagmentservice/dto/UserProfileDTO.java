package it.unisalento.pasproject.profilemanagmentservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileDTO extends GenericProfileDTO {
    private String residenceCity;
    private String residenceAddress;
    private String phoneNumber;
    private String fiscalCode;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime birthDate;
    private String cardNumber;
    private String cardExpiryDate;
    private String cardCvv;

    public UserProfileDTO() {}
}
