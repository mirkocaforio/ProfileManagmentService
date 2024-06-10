package it.unisalento.pasproject.profilemanagmentservice.service;

import it.unisalento.pasproject.profilemanagmentservice.domain.*;
import it.unisalento.pasproject.profilemanagmentservice.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    private final MongoTemplate mongoTemplate;

    private final ProfileDTOFactory profileDTOFactory;

    private final ProfileFactory profileFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    public ProfileService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.profileDTOFactory = new ProfileDTOFactory();
        this.profileFactory = new ProfileFactory();
    }

    public UserProfile getUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = (UserProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.UTENTE);

        Optional.ofNullable(userProfileDTO.getId()).ifPresent(userProfile::setId);
        Optional.ofNullable(userProfileDTO.getName()).ifPresent(userProfile::setName);
        Optional.ofNullable(userProfileDTO.getSurname()).ifPresent(userProfile::setSurname);
        Optional.ofNullable(userProfileDTO.getEmail()).ifPresent(userProfile::setEmail);
        Optional.ofNullable(userProfileDTO.getRole()).ifPresent(userProfile::setRole);
        Optional.of(userProfileDTO.isEnabled()).ifPresent(userProfile::setEnabled);
        Optional.ofNullable(userProfileDTO.getRegistrationDate()).ifPresent(userProfile::setRegistrationDate);
        Optional.ofNullable(userProfileDTO.getResidenceCity()).ifPresent(userProfile::setResidenceCity);
        Optional.ofNullable(userProfileDTO.getResidenceAddress()).ifPresent(userProfile::setResidenceAddress);
        Optional.ofNullable(userProfileDTO.getPhoneNumber()).ifPresent(userProfile::setPhoneNumber);
        Optional.ofNullable(userProfileDTO.getFiscalCode()).ifPresent(userProfile::setFiscalCode);
        Optional.ofNullable(userProfileDTO.getBirthDate()).ifPresent(userProfile::setBirthDate);

        return userProfile;
    }

    public UserProfile getUserProfile(RegistrationDTO registrationDTO) {
        UserProfile userProfile = (UserProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.UTENTE);

        Optional.ofNullable(registrationDTO.getName()).ifPresent(userProfile::setName);
        Optional.ofNullable(registrationDTO.getSurname()).ifPresent(userProfile::setSurname);
        Optional.ofNullable(registrationDTO.getEmail()).ifPresent(userProfile::setEmail);
        Optional.ofNullable(registrationDTO.getRole()).ifPresent(userProfile::setRole);
        userProfile.setEnabled(true);
        Optional.ofNullable(registrationDTO.getRegistrationDate()).ifPresent(userProfile::setRegistrationDate);

        return userProfile;
    }

    public MemberProfile getMemberProfile(MemberProfileDTO memberProfileDTO) {
        MemberProfile memberProfile = (MemberProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.MEMBRO);

        Optional.ofNullable(memberProfileDTO.getId()).ifPresent(memberProfile::setId);
        Optional.ofNullable(memberProfileDTO.getName()).ifPresent(memberProfile::setName);
        Optional.ofNullable(memberProfileDTO.getSurname()).ifPresent(memberProfile::setSurname);
        Optional.ofNullable(memberProfileDTO.getEmail()).ifPresent(memberProfile::setEmail);
        Optional.ofNullable(memberProfileDTO.getRole()).ifPresent(memberProfile::setRole);
        Optional.of(memberProfileDTO.isEnabled()).ifPresent(memberProfile::setEnabled);
        Optional.ofNullable(memberProfileDTO.getRegistrationDate()).ifPresent(memberProfile::setRegistrationDate);
        Optional.ofNullable(memberProfileDTO.getResidenceCity()).ifPresent(memberProfile::setResidenceCity);
        Optional.ofNullable(memberProfileDTO.getResidenceAddress()).ifPresent(memberProfile::setResidenceAddress);
        Optional.ofNullable(memberProfileDTO.getPhoneNumber()).ifPresent(memberProfile::setPhoneNumber);
        Optional.ofNullable(memberProfileDTO.getFiscalCode()).ifPresent(memberProfile::setFiscalCode);
        Optional.ofNullable(memberProfileDTO.getBirthDate()).ifPresent(memberProfile::setBirthDate);

        return memberProfile;
    }

    public MemberProfile getMemberProfile(RegistrationDTO registrationDTO) {
        MemberProfile memberProfile = (MemberProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.MEMBRO);

        Optional.ofNullable(registrationDTO.getName()).ifPresent(memberProfile::setName);
        Optional.ofNullable(registrationDTO.getSurname()).ifPresent(memberProfile::setSurname);
        Optional.ofNullable(registrationDTO.getEmail()).ifPresent(memberProfile::setEmail);
        Optional.ofNullable(registrationDTO.getRole()).ifPresent(memberProfile::setRole);
        memberProfile.setEnabled(true);
        Optional.ofNullable(registrationDTO.getRegistrationDate()).ifPresent(memberProfile::setRegistrationDate);

        return memberProfile;
    }

    public AdminProfile getAdminProfile(AdminProfileDTO adminProfileDTO) {
        AdminProfile adminProfile = (AdminProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.ADMIN);

        Optional.ofNullable(adminProfileDTO.getId()).ifPresent(adminProfile::setId);
        Optional.ofNullable(adminProfileDTO.getName()).ifPresent(adminProfile::setName);
        Optional.ofNullable(adminProfileDTO.getSurname()).ifPresent(adminProfile::setSurname);
        Optional.ofNullable(adminProfileDTO.getEmail()).ifPresent(adminProfile::setEmail);
        Optional.ofNullable(adminProfileDTO.getRole()).ifPresent(adminProfile::setRole);
        Optional.of(adminProfileDTO.isEnabled()).ifPresent(adminProfile::setEnabled);
        Optional.ofNullable(adminProfileDTO.getRegistrationDate()).ifPresent(adminProfile::setRegistrationDate);

        return adminProfile;
    }

    public AdminProfile getAdminProfile(RegistrationDTO registrationDTO) {
        AdminProfile adminProfile = (AdminProfile) profileFactory.getProfileType(ProfileFactory.ProfileType.ADMIN);

        Optional.ofNullable(registrationDTO.getName()).ifPresent(adminProfile::setName);
        Optional.ofNullable(registrationDTO.getSurname()).ifPresent(adminProfile::setSurname);
        Optional.ofNullable(registrationDTO.getEmail()).ifPresent(adminProfile::setEmail);
        Optional.ofNullable(registrationDTO.getRole()).ifPresent(adminProfile::setRole);
        adminProfile.setEnabled(true);
        Optional.ofNullable(registrationDTO.getRegistrationDate()).ifPresent(adminProfile::setRegistrationDate);

        return adminProfile;
    }

    public UserProfileDTO getUserProfileDTO(UserProfile userProfile) {
        UserProfileDTO userProfileDTO = (UserProfileDTO) profileDTOFactory.getProfileType(ProfileDTOFactory.ProfileDTOType.UTENTE);

        userProfileDTO.setId(userProfile.getId());
        userProfileDTO.setName(userProfile.getName());
        userProfileDTO.setSurname(userProfile.getSurname());
        userProfileDTO.setEmail(userProfile.getEmail());
        userProfileDTO.setRole(userProfile.getRole());
        userProfileDTO.setEnabled(userProfile.isEnabled());
        userProfileDTO.setRegistrationDate(userProfile.getRegistrationDate());
        userProfileDTO.setResidenceCity(userProfile.getResidenceCity());
        userProfileDTO.setResidenceAddress(userProfile.getResidenceAddress());
        userProfileDTO.setPhoneNumber(userProfile.getPhoneNumber());
        userProfileDTO.setFiscalCode(userProfile.getFiscalCode());
        userProfileDTO.setBirthDate(userProfile.getBirthDate());

        return userProfileDTO;
    }

    public MemberProfileDTO getMemberProfileDTO(MemberProfile memberProfile) {
        MemberProfileDTO memberProfileDTO = (MemberProfileDTO) profileDTOFactory.getProfileType(ProfileDTOFactory.ProfileDTOType.MEMBRO);

        memberProfileDTO.setId(memberProfile.getId());
        memberProfileDTO.setName(memberProfile.getName());
        memberProfileDTO.setSurname(memberProfile.getSurname());
        memberProfileDTO.setEmail(memberProfile.getEmail());
        memberProfileDTO.setRole(memberProfile.getRole());
        memberProfileDTO.setEnabled(memberProfile.isEnabled());
        memberProfileDTO.setRegistrationDate(memberProfile.getRegistrationDate());
        memberProfileDTO.setResidenceCity(memberProfile.getResidenceCity());
        memberProfileDTO.setResidenceAddress(memberProfile.getResidenceAddress());
        memberProfileDTO.setPhoneNumber(memberProfile.getPhoneNumber());
        memberProfileDTO.setFiscalCode(memberProfile.getFiscalCode());
        memberProfileDTO.setBirthDate(memberProfile.getBirthDate());

        return memberProfileDTO;
    }

    public AdminProfileDTO getAdminProfileDTO(AdminProfile adminProfile) {
        AdminProfileDTO adminProfileDTO = (AdminProfileDTO) profileDTOFactory.getProfileType(ProfileDTOFactory.ProfileDTOType.ADMIN);

        adminProfileDTO.setId(adminProfile.getId());
        adminProfileDTO.setName(adminProfile.getName());
        adminProfileDTO.setSurname(adminProfile.getSurname());
        adminProfileDTO.setEmail(adminProfile.getEmail());
        adminProfileDTO.setRole(adminProfile.getRole());
        adminProfileDTO.setEnabled(adminProfile.isEnabled());
        adminProfileDTO.setRegistrationDate(adminProfile.getRegistrationDate());

        return adminProfileDTO;
    }

    public GenericProfile updateProfile(GenericProfile profile, GenericProfileDTO profileDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Verifica che l'email dell'utente autenticato coincida con quella nel DTO
        if (!userDetails.getUsername().equals(profileDTO.getEmail())) {
            throw new AccessDeniedException("Authenticated user email does not match the profile email");
        }

        Optional.ofNullable(profileDTO.getName()).ifPresent(profile::setName);
        Optional.ofNullable(profileDTO.getSurname()).ifPresent(profile::setSurname);

        return profile;
    }

    public UserProfile updateUserProfile(UserProfile userProfile, UserProfileDTO userProfileDTO) {
        userProfile = (UserProfile) updateProfile(userProfile, userProfileDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Verifica che l'email dell'utente autenticato coincida con quella nel DTO
        if (!userDetails.getUsername().equals(userProfileDTO.getEmail())) {
            throw new AccessDeniedException("Authenticated user email does not match the profile email");
        }

        Optional.ofNullable(userProfileDTO.getResidenceCity()).ifPresent(userProfile::setResidenceCity);
        Optional.ofNullable(userProfileDTO.getResidenceAddress()).ifPresent(userProfile::setResidenceAddress);
        Optional.ofNullable(userProfileDTO.getPhoneNumber()).ifPresent(userProfile::setPhoneNumber);
        Optional.ofNullable(userProfileDTO.getFiscalCode()).ifPresent(userProfile::setFiscalCode);
        Optional.ofNullable(userProfileDTO.getBirthDate()).ifPresent(userProfile::setBirthDate);

        return userProfile;
    }

    public MemberProfile updateMemberProfile(MemberProfile memberProfile, MemberProfileDTO memberProfileDTO) {
        memberProfile = (MemberProfile) updateProfile(memberProfile, memberProfileDTO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Verifica che l'email dell'utente autenticato coincida con quella nel DTO
        if (!userDetails.getUsername().equals(memberProfileDTO.getEmail())) {
            throw new AccessDeniedException("Authenticated user email does not match the profile email");
        }

        Optional.ofNullable(memberProfileDTO.getResidenceCity()).ifPresent(memberProfile::setResidenceCity);
        Optional.ofNullable(memberProfileDTO.getResidenceAddress()).ifPresent(memberProfile::setResidenceAddress);
        Optional.ofNullable(memberProfileDTO.getPhoneNumber()).ifPresent(memberProfile::setPhoneNumber);
        Optional.ofNullable(memberProfileDTO.getFiscalCode()).ifPresent(memberProfile::setFiscalCode);
        Optional.ofNullable(memberProfileDTO.getBirthDate()).ifPresent(memberProfile::setBirthDate);

        return memberProfile;
    }

    public UpdatedProfileMessageDTO getUpdatedProfileMessageDTO(UserProfile userProfile) {
        UpdatedProfileMessageDTO updatedProfileMessageDTO = new UpdatedProfileMessageDTO();

        Optional.ofNullable(userProfile.getName()).ifPresent(updatedProfileMessageDTO::setName);
        Optional.ofNullable(userProfile.getSurname()).ifPresent(updatedProfileMessageDTO::setSurname);
        Optional.ofNullable(userProfile.getEmail()).ifPresent(updatedProfileMessageDTO::setEmail);
        Optional.ofNullable(userProfile.getRole()).ifPresent(updatedProfileMessageDTO::setRole);
        Optional.of(userProfile.isEnabled()).ifPresent(updatedProfileMessageDTO::setEnabled);
        Optional.ofNullable(userProfile.getRegistrationDate()).ifPresent(updatedProfileMessageDTO::setRegistrationDate);
        Optional.ofNullable(userProfile.getResidenceCity()).ifPresent(updatedProfileMessageDTO::setResidenceCity);
        Optional.ofNullable(userProfile.getResidenceAddress()).ifPresent(updatedProfileMessageDTO::setResidenceAddress);
        Optional.ofNullable(userProfile.getPhoneNumber()).ifPresent(updatedProfileMessageDTO::setPhoneNumber);
        Optional.ofNullable(userProfile.getFiscalCode()).ifPresent(updatedProfileMessageDTO::setFiscalCode);
        Optional.ofNullable(userProfile.getBirthDate()).ifPresent(updatedProfileMessageDTO::setBirthDate);

        return updatedProfileMessageDTO;
    }

    public UpdatedProfileMessageDTO getUpdatedProfileMessageDTO(MemberProfile memberProfile) {
        UpdatedProfileMessageDTO updatedProfileMessageDTO = new UpdatedProfileMessageDTO();

        Optional.ofNullable(memberProfile.getName()).ifPresent(updatedProfileMessageDTO::setName);
        Optional.ofNullable(memberProfile.getSurname()).ifPresent(updatedProfileMessageDTO::setSurname);
        Optional.ofNullable(memberProfile.getEmail()).ifPresent(updatedProfileMessageDTO::setEmail);
        Optional.ofNullable(memberProfile.getRole()).ifPresent(updatedProfileMessageDTO::setRole);
        Optional.of(memberProfile.isEnabled()).ifPresent(updatedProfileMessageDTO::setEnabled);
        Optional.ofNullable(memberProfile.getRegistrationDate()).ifPresent(updatedProfileMessageDTO::setRegistrationDate);
        Optional.ofNullable(memberProfile.getResidenceCity()).ifPresent(updatedProfileMessageDTO::setResidenceCity);
        Optional.ofNullable(memberProfile.getResidenceAddress()).ifPresent(updatedProfileMessageDTO::setResidenceAddress);
        Optional.ofNullable(memberProfile.getPhoneNumber()).ifPresent(updatedProfileMessageDTO::setPhoneNumber);
        Optional.ofNullable(memberProfile.getFiscalCode()).ifPresent(updatedProfileMessageDTO::setFiscalCode);
        Optional.ofNullable(memberProfile.getBirthDate()).ifPresent(updatedProfileMessageDTO::setBirthDate);

        return updatedProfileMessageDTO;
    }

    public UpdatedProfileMessageDTO getUpdatedProfileMessageDTO(AdminProfile adminProfile) {
        UpdatedProfileMessageDTO updatedProfileMessageDTO = new UpdatedProfileMessageDTO();

        Optional.ofNullable(adminProfile.getName()).ifPresent(updatedProfileMessageDTO::setName);
        Optional.ofNullable(adminProfile.getSurname()).ifPresent(updatedProfileMessageDTO::setSurname);
        Optional.ofNullable(adminProfile.getEmail()).ifPresent(updatedProfileMessageDTO::setEmail);
        Optional.ofNullable(adminProfile.getRole()).ifPresent(updatedProfileMessageDTO::setRole);
        Optional.of(adminProfile.isEnabled()).ifPresent(updatedProfileMessageDTO::setEnabled);
        Optional.ofNullable(adminProfile.getRegistrationDate()).ifPresent(updatedProfileMessageDTO::setRegistrationDate);

        return updatedProfileMessageDTO;
    }

    public List<GenericProfile> findProfiles(String role, String name, String surname, String email, Boolean isEnabled, LocalDateTime registrationDate, String residenceCity, String residenceAddress, String phoneNumber, String fiscalCode, LocalDateTime birthDate) {
        Query query = new Query();

        query.addCriteria(Criteria.where("role").is(role));

        query.addCriteria(Criteria.where("isEnabled").is(Objects.requireNonNullElse(isEnabled, true)));

        if (name != null) {
            query.addCriteria(Criteria.where("name").is(name));
        }

        if (surname != null) {
            query.addCriteria(Criteria.where("surname").is(surname));
        }

        if (email != null) {
            query.addCriteria(Criteria.where("email").is(email));
        }

        if (registrationDate != null) {
            query.addCriteria(Criteria.where("registrationDate").is(registrationDate));
        }

        if (residenceCity != null) {
            query.addCriteria(Criteria.where("residenceCity").is(residenceCity));
        }

        if (residenceAddress != null) {
            query.addCriteria(Criteria.where("residenceAddress").is(residenceAddress));
        }

        if (phoneNumber != null) {
            query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
        }

        if (fiscalCode != null) {
            query.addCriteria(Criteria.where("fiscalCode").is(fiscalCode));
        }

        if (birthDate != null) {
            query.addCriteria(Criteria.where("birthDate").is(birthDate));
        }

        LOGGER.info("\n{}\n", query);

        List<GenericProfile> profiles = mongoTemplate.find(query, GenericProfile.class, mongoTemplate.getCollectionName(GenericProfile.class));

        LOGGER.info("\nProfiles: {}\n", profiles);

        return profiles;
    }
}
