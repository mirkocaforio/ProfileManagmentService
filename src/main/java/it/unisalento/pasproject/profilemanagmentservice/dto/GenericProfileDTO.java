package it.unisalento.pasproject.profilemanagmentservice.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "role",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserProfileDTO.class, name = "UTENTE"),
        @JsonSubTypes.Type(value = MemberProfileDTO.class, name = "MEMBRO"),
        @JsonSubTypes.Type(value = AdminProfileDTO.class, name = "ADMIN")
})
@Getter
@Setter
public abstract class GenericProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private boolean isEnabled;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registrationDate;

    public GenericProfileDTO() {}
}
