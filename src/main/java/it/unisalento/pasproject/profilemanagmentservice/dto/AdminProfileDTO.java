package it.unisalento.pasproject.profilemanagmentservice.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * AdminProfileDTO is a data transfer object that extends the GenericProfileDTO.
 * It is used to transfer data between processes or across network connections.
 * This class uses Lombok annotations for automatic getter and setter methods.
 *
 * @author mirkocaforio
 * @version 1.0
 * @since 2024.1.1
 */
@Getter
@Setter
public class AdminProfileDTO extends GenericProfileDTO {
    /**
     * Default constructor for AdminProfileDTO.
     */
    public AdminProfileDTO() {}
}