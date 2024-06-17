package it.unisalento.pasproject.profilemanagmentservice.controller;

import it.unisalento.pasproject.profilemanagmentservice.domain.AdminProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.GenericProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.MemberProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.UserProfile;
import it.unisalento.pasproject.profilemanagmentservice.dto.*;
import it.unisalento.pasproject.profilemanagmentservice.exceptions.InvalidProfileTypeException;
import it.unisalento.pasproject.profilemanagmentservice.exceptions.ProfileNotFoundException;
import it.unisalento.pasproject.profilemanagmentservice.repositories.ProfileRepository;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileMessageHandler;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static it.unisalento.pasproject.profilemanagmentservice.security.SecurityConstants.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    final ProfileRepository profileRepository;

    private final ProfileService profileService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileMessageHandler profileMessageHandler;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, ProfileService profileService, ProfileMessageHandler profileMessageHandler) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
        this.profileMessageHandler = profileMessageHandler;
    }

    @GetMapping("/find/all")
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileListDTO getAllProfiles() {
        GenericProfileListDTO profileList = new GenericProfileListDTO();
        List<GenericProfileDTO> list = new ArrayList<>();
        profileList.setProfilesList(list);

        List<GenericProfile> profiles = profileRepository.findAll();

        for (GenericProfile profile : profiles) {
            if (profile instanceof UserProfile) {
                LOGGER.info("UserProfile found: {}", profile.getEmail());
                list.add(profileService.getUserProfileDTO((UserProfile) profile));
            } else if (profile instanceof MemberProfile) {
                LOGGER.info("MemberProfile found: {}", profile.getEmail());
                list.add(profileService.getMemberProfileDTO((MemberProfile) profile));
            } else if (profile instanceof AdminProfile) {
                LOGGER.info("AdminProfile found: {}", profile.getEmail());
                list.add(profileService.getAdminProfileDTO((AdminProfile) profile));
            }
        }

        return profileList;
    }

    @GetMapping("/find")
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileListDTO getProfileWithFilters(@RequestParam String role,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String surname,
                                                       @RequestParam(required = false) String email,
                                                       @RequestParam(required = false) Boolean isEnabled,
                                                       @RequestParam(required = false) LocalDateTime registrationDate,
                                                       @RequestParam(required = false) String residenceCity,
                                                       @RequestParam(required = false) String residenceAddress,
                                                       @RequestParam(required = false) String phoneNumber,
                                                       @RequestParam(required = false) String fiscalCode,
                                                       @RequestParam(required = false) LocalDateTime birthDate) throws ProfileNotFoundException {
        GenericProfileListDTO profileList = new GenericProfileListDTO();
        List<GenericProfileDTO> list = new ArrayList<>();
        profileList.setProfilesList(list);

        List<GenericProfile> profiles = profileService.findProfiles(role, name, surname, email, isEnabled, registrationDate, residenceCity, residenceAddress, phoneNumber, fiscalCode, birthDate);

        if (profiles.isEmpty())
            throw new ProfileNotFoundException("No profiles found with the specified filters.");

        for (GenericProfile profile : profiles) {
            if (profile instanceof UserProfile) {
                list.add(profileService.getUserProfileDTO((UserProfile) profile));
            } else if (profile instanceof MemberProfile) {
                list.add(profileService.getMemberProfileDTO((MemberProfile) profile));
            } else if (profile instanceof AdminProfile) {
                list.add(profileService.getAdminProfileDTO((AdminProfile) profile));
            }
        }

        return profileList;
    }

    @PutMapping(value = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileDTO updateProfile(@RequestBody GenericProfileDTO profileToUpdate) throws ProfileNotFoundException {

        if(profileToUpdate.getEmail() == null)
            throw new ProfileNotFoundException("Missing email.");

        Optional<GenericProfile> profile = profileRepository.findByEmail(profileToUpdate.getEmail());

        if (profile.isEmpty())
            throw new ProfileNotFoundException("Profile not found with email: " + profileToUpdate.getEmail() + ".");

        GenericProfile retProfile = profile.get();

        retProfile = profileService.updateProfile(retProfile, profileToUpdate);

        switch (retProfile) {
            case UserProfile retUserProfile -> {
                UserProfileDTO userProfileDTO = (UserProfileDTO) profileToUpdate;

                retUserProfile = profileService.updateUserProfile(retUserProfile, userProfileDTO);

                retUserProfile = profileRepository.save(retUserProfile);

                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retUserProfile));

                return profileService.getUserProfileDTO(retUserProfile);
            }
            case MemberProfile retMemberProfile -> {
                MemberProfileDTO memberProfileDTO = (MemberProfileDTO) profileToUpdate;

                retMemberProfile = profileService.updateMemberProfile(retMemberProfile, memberProfileDTO);

                retMemberProfile = profileRepository.save(retMemberProfile);

                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retMemberProfile));

                return profileService.getMemberProfileDTO(retMemberProfile);
            }
            case AdminProfile retAdminProfile -> {

                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retAdminProfile));

                return profileService.getAdminProfileDTO(retAdminProfile);
            }
            default -> throw new IllegalArgumentException("Invalid profile type: " + retProfile.getClass());
        }
    }

    @PutMapping("/enable/{profileEmail}")
    @Secured(ROLE_ADMIN)
    public GenericProfileDTO enableProfile(@PathVariable String profileEmail) throws ProfileNotFoundException {
        Optional<GenericProfile> profile = profileRepository.findByEmail(profileEmail);

        if (profile.isEmpty())
            throw new ProfileNotFoundException("Profile not found with id: " + profileEmail + ".");

        GenericProfile retProfile = profile.get();

        retProfile.setEnabled(true);

        retProfile = profileRepository.save(retProfile);

        switch (retProfile) {
            case UserProfile retUserProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retUserProfile));
                return profileService.getUserProfileDTO(retUserProfile);
            }
            case MemberProfile retMemberProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retMemberProfile));
                return profileService.getMemberProfileDTO(retMemberProfile);
            }
            case AdminProfile retAdminProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retAdminProfile));
                return profileService.getAdminProfileDTO(retAdminProfile);
            }
            default -> throw new InvalidProfileTypeException("Invalid profile type: " + retProfile.getClass());
        }
    }

    @PutMapping("/disable/{profileEmail}")
    @Secured(ROLE_ADMIN)
    public GenericProfileDTO disableProfile(@PathVariable String profileEmail) throws ProfileNotFoundException {
        Optional<GenericProfile> profile = profileRepository.findByEmail(profileEmail);

        if (profile.isEmpty())
            throw new ProfileNotFoundException("Profile not found with email: " + profileEmail + ".");

        GenericProfile retProfile = profile.get();

        retProfile.setEnabled(false);

        retProfile = profileRepository.save(retProfile);

        switch (retProfile) {
            case UserProfile retUserProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retUserProfile));
                return profileService.getUserProfileDTO(retUserProfile);
            }
            case MemberProfile retMemberProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retMemberProfile));
                return profileService.getMemberProfileDTO(retMemberProfile);
            }
            case AdminProfile retAdminProfile -> {
                profileMessageHandler.sendProfileMessage(profileService.getUpdatedProfileMessageDTO(retAdminProfile));
                return profileService.getAdminProfileDTO(retAdminProfile);
            }
            default -> throw new InvalidProfileTypeException("Invalid profile type: " + retProfile.getClass());
        }
    }

}
