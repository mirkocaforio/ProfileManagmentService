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

import java.util.List;
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
        Optional.ofNullable(userProfileDTO.getCardNumber()).ifPresent(userProfile::setCardNumber);
        Optional.ofNullable(userProfileDTO.getCardExpiryDate()).ifPresent(userProfile::setCardExpiryDate);
        Optional.ofNullable(userProfileDTO.getCardCvv()).ifPresent(userProfile::setCardCvv);

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

        Optional.ofNullable(userProfile.getId()).ifPresent(userProfileDTO::setId);
        Optional.ofNullable(userProfile.getName()).ifPresent(userProfileDTO::setName);
        Optional.ofNullable(userProfile.getSurname()).ifPresent(userProfileDTO::setSurname);
        Optional.ofNullable(userProfile.getEmail()).ifPresent(userProfileDTO::setEmail);
        Optional.ofNullable(userProfile.getRole()).ifPresent(userProfileDTO::setRole);
        Optional.of(userProfile.isEnabled()).ifPresent(userProfileDTO::setEnabled);
        Optional.ofNullable(userProfile.getRegistrationDate()).ifPresent(userProfileDTO::setRegistrationDate);
        Optional.ofNullable(userProfile.getResidenceCity()).ifPresent(userProfileDTO::setResidenceCity);
        Optional.ofNullable(userProfile.getResidenceAddress()).ifPresent(userProfileDTO::setResidenceAddress);
        Optional.ofNullable(userProfile.getPhoneNumber()).ifPresent(userProfileDTO::setPhoneNumber);
        Optional.ofNullable(userProfile.getFiscalCode()).ifPresent(userProfileDTO::setFiscalCode);
        Optional.ofNullable(userProfile.getBirthDate()).ifPresent(userProfileDTO::setBirthDate);
        Optional.ofNullable(userProfile.getCardNumber()).ifPresent(userProfileDTO::setCardNumber);
        Optional.ofNullable(userProfile.getCardExpiryDate()).ifPresent(userProfileDTO::setCardExpiryDate);
        Optional.ofNullable(userProfile.getCardCvv()).ifPresent(userProfileDTO::setCardCvv);

        return userProfileDTO;
    }

    public MemberProfileDTO getMemberProfileDTO(MemberProfile memberProfile) {
        MemberProfileDTO memberProfileDTO = (MemberProfileDTO) profileDTOFactory.getProfileType(ProfileDTOFactory.ProfileDTOType.MEMBRO);

        Optional.ofNullable(memberProfile.getId()).ifPresent(memberProfileDTO::setId);
        Optional.ofNullable(memberProfile.getName()).ifPresent(memberProfileDTO::setName);
        Optional.ofNullable(memberProfile.getSurname()).ifPresent(memberProfileDTO::setSurname);
        Optional.ofNullable(memberProfile.getEmail()).ifPresent(memberProfileDTO::setEmail);
        Optional.ofNullable(memberProfile.getRole()).ifPresent(memberProfileDTO::setRole);
        Optional.of(memberProfile.isEnabled()).ifPresent(memberProfileDTO::setEnabled);
        Optional.ofNullable(memberProfile.getRegistrationDate()).ifPresent(memberProfileDTO::setRegistrationDate);
        Optional.ofNullable(memberProfile.getResidenceCity()).ifPresent(memberProfileDTO::setResidenceCity);
        Optional.ofNullable(memberProfile.getResidenceAddress()).ifPresent(memberProfileDTO::setResidenceAddress);
        Optional.ofNullable(memberProfile.getPhoneNumber()).ifPresent(memberProfileDTO::setPhoneNumber);
        Optional.ofNullable(memberProfile.getFiscalCode()).ifPresent(memberProfileDTO::setFiscalCode);
        Optional.ofNullable(memberProfile.getBirthDate()).ifPresent(memberProfileDTO::setBirthDate);

        return memberProfileDTO;
    }

    public AdminProfileDTO getAdminProfileDTO(AdminProfile adminProfile) {
        AdminProfileDTO adminProfileDTO = (AdminProfileDTO) profileDTOFactory.getProfileType(ProfileDTOFactory.ProfileDTOType.ADMIN);

        Optional.ofNullable(adminProfile.getId()).ifPresent(adminProfileDTO::setId);
        Optional.ofNullable(adminProfile.getName()).ifPresent(adminProfileDTO::setName);
        Optional.ofNullable(adminProfile.getSurname()).ifPresent(adminProfileDTO::setSurname);
        Optional.ofNullable(adminProfile.getEmail()).ifPresent(adminProfileDTO::setEmail);
        Optional.ofNullable(adminProfile.getRole()).ifPresent(adminProfileDTO::setRole);
        Optional.of(adminProfile.isEnabled()).ifPresent(adminProfileDTO::setEnabled);
        Optional.ofNullable(adminProfile.getRegistrationDate()).ifPresent(adminProfileDTO::setRegistrationDate);

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
        Optional.ofNullable(userProfileDTO.getCardNumber()).ifPresent(userProfile::setCardNumber);
        Optional.ofNullable(userProfileDTO.getCardExpiryDate()).ifPresent(userProfile::setCardExpiryDate);
        Optional.ofNullable(userProfileDTO.getCardCvv()).ifPresent(userProfile::setCardCvv);

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
        Optional.ofNullable(userProfile.getCardNumber()).ifPresent(updatedProfileMessageDTO::setCardNumber);
        Optional.ofNullable(userProfile.getCardExpiryDate()).ifPresent(updatedProfileMessageDTO::setCardExpiryDate);
        Optional.ofNullable(userProfile.getCardCvv()).ifPresent(updatedProfileMessageDTO::setCardCvv);

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

    public PaymentInfoMessageDTO getPaymentInfoMessageDTO(UserProfile userProfile) {
        PaymentInfoMessageDTO paymentInfoMessageDTO = new PaymentInfoMessageDTO();

        Optional.ofNullable(userProfile.getName()).ifPresent(paymentInfoMessageDTO::setName);
        Optional.ofNullable(userProfile.getSurname()).ifPresent(paymentInfoMessageDTO::setSurname);
        Optional.ofNullable(userProfile.getEmail()).ifPresent(paymentInfoMessageDTO::setEmail);
        Optional.of(userProfile.isEnabled()).ifPresent(paymentInfoMessageDTO::setEnabled);
        Optional.ofNullable(userProfile.getRegistrationDate()).ifPresent(paymentInfoMessageDTO::setRegistrationDate);
        Optional.ofNullable(userProfile.getCardNumber()).ifPresent(paymentInfoMessageDTO::setCardNumber);
        Optional.ofNullable(userProfile.getCardExpiryDate()).ifPresent(paymentInfoMessageDTO::setCardExpiryDate);
        Optional.ofNullable(userProfile.getCardCvv()).ifPresent(paymentInfoMessageDTO::setCardCvv);

        return paymentInfoMessageDTO;
    }

    public List<GenericProfile> findProfiles(ProfileQueryFilters profileQueryFilters) {
        Query query = new Query();

        if (profileQueryFilters.getRole() != null) {
            query.addCriteria(Criteria.where("role").is(profileQueryFilters.getRole()));
        }

        if (profileQueryFilters.getName() != null) {
            query.addCriteria(Criteria.where("name").is(profileQueryFilters.getName()));
        }

        if (profileQueryFilters.getSurname() != null) {
            query.addCriteria(Criteria.where("surname").is(profileQueryFilters.getSurname()));
        }

        if (profileQueryFilters.getEmail() != null) {
            query.addCriteria(Criteria.where("email").is(profileQueryFilters.getEmail()));
        }

        if (profileQueryFilters.getIsEnabled() != null) {
            query.addCriteria(Criteria.where("isEnabled").is(profileQueryFilters.getIsEnabled()));
        }

        if (profileQueryFilters.getRegistrationDate() != null) {
            query.addCriteria(Criteria.where("registrationDate").is(profileQueryFilters.getRegistrationDate()));
        }

        if (profileQueryFilters.getResidenceCity() != null) {
            query.addCriteria(Criteria.where("residenceCity").is(profileQueryFilters.getResidenceCity()));
        }

        if (profileQueryFilters.getResidenceAddress() != null) {
            query.addCriteria(Criteria.where("residenceAddress").is(profileQueryFilters.getResidenceAddress()));
        }

        if (profileQueryFilters.getPhoneNumber() != null) {
            query.addCriteria(Criteria.where("phoneNumber").is(profileQueryFilters.getPhoneNumber()));
        }

        if (profileQueryFilters.getFiscalCode() != null) {
            query.addCriteria(Criteria.where("fiscalCode").is(profileQueryFilters.getFiscalCode()));
        }

        if (profileQueryFilters.getBirthDate() != null) {
            query.addCriteria(Criteria.where("birthDate").is(profileQueryFilters.getBirthDate()));
        }

        LOGGER.info("\n{}\n", query);

        List<GenericProfile> profiles = mongoTemplate.find(query, GenericProfile.class, mongoTemplate.getCollectionName(GenericProfile.class));

        LOGGER.info("\nProfiles: {}\n", profiles);

        return profiles;
    }
}
