package it.unisalento.pasproject.profilemanagmentservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document("profile")
public abstract class GenericProfile {
    @Id
    private String id;  //TODO: vedere se posso settarlo con il dto oppure creare un altro id
    private String name;
    private String surname;
    private String email;
    private String role;
    private boolean isEnabled;
    private LocalDateTime registrationDate;

    public GenericProfile() {}
}
