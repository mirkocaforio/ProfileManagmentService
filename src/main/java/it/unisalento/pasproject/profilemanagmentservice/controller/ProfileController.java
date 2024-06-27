package it.unisalento.pasproject.profilemanagmentservice.controller;

import it.unisalento.pasproject.profilemanagmentservice.dto.*;
import it.unisalento.pasproject.profilemanagmentservice.exceptions.ProfileNotFoundException;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileQueryFilters;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static it.unisalento.pasproject.profilemanagmentservice.security.SecurityConstants.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/find/all")
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileListDTO getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/find")
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileListDTO getProfileWithFilters(@ModelAttribute ProfileQueryFilters profileQueryFilters) throws ProfileNotFoundException {
        return profileService.getProfileWithFilters(profileQueryFilters);
    }

    @PutMapping(value = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured({ROLE_ADMIN, ROLE_UTENTE, ROLE_MEMBRO})
    public GenericProfileDTO updateProfile(@RequestBody GenericProfileDTO profileToUpdate) throws ProfileNotFoundException {
        if(profileToUpdate.getEmail() == null)
            throw new ProfileNotFoundException("Missing email.");

        return profileService.updateProfile(profileToUpdate);
    }

    @PutMapping("/enable/{profileEmail}")
    @Secured(ROLE_ADMIN)
    public GenericProfileDTO enableProfile(@PathVariable String profileEmail) throws ProfileNotFoundException {
        return profileService.enableProfile(profileEmail);
    }

    @PutMapping("/disable/{profileEmail}")
    @Secured(ROLE_ADMIN)
    public GenericProfileDTO disableProfile(@PathVariable String profileEmail) throws ProfileNotFoundException {
        return profileService.disableProfile(profileEmail);
    }

}
