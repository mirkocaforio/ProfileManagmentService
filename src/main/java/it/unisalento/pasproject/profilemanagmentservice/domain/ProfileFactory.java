package it.unisalento.pasproject.profilemanagmentservice.domain;

public class ProfileFactory {
    public enum ProfileType {
        UTENTE,
        MEMBRO,
        ADMIN
    }

    public GenericProfile getProfileType(ProfileType type) {
        if (type == null) {;
            type = ProfileType.UTENTE;
        }

        return switch (type) {
            case UTENTE -> new UserProfile();
            case MEMBRO -> new MemberProfile();
            case ADMIN -> new AdminProfile();
        };
    }
}
