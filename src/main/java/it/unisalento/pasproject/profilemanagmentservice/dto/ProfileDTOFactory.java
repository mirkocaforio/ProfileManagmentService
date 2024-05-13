package it.unisalento.pasproject.profilemanagmentservice.dto;

public class ProfileDTOFactory {
    public enum ProfileDTOType {
        UTENTE,
        MEMBRO,
        ADMIN
    }

    public GenericProfileDTO getProfileType(ProfileDTOType type) {
        if (type == null) {;
            type = ProfileDTOType.UTENTE;
        }

        return switch (type) {
            case UTENTE -> new UserProfileDTO();
            case MEMBRO -> new MemberProfileDTO();
            case ADMIN -> new AdminProfileDTO();
        };
    }
}
