package it.unisalento.pasproject.profilemanagmentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GenericProfileListDTO {
    private List<GenericProfileDTO> profilesList;

    public GenericProfileListDTO() {
        this.profilesList = new ArrayList<>();
    }
}