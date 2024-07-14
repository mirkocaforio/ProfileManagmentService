package it.unisalento.pasproject.profilemanagmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.pasproject.profilemanagmentservice.TestSecurityConfig;
import it.unisalento.pasproject.profilemanagmentservice.domain.AdminProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.GenericProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.MemberProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.UserProfile;
import it.unisalento.pasproject.profilemanagmentservice.dto.*;
import it.unisalento.pasproject.profilemanagmentservice.repositories.ProfileRepository;
import it.unisalento.pasproject.profilemanagmentservice.service.PaymentInfoMessageHandler;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileMessageHandler;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileQueryFilters;
import it.unisalento.pasproject.profilemanagmentservice.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(TestSecurityConfig.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private ProfileMessageHandler profileMessageHandler;

    @MockBean
    private PaymentInfoMessageHandler paymentInfoMessageHandler;

    private List<GenericProfile> profiles;

    private static final Logger logger = LoggerFactory.getLogger(ProfileControllerTest.class);

    @BeforeEach
    void setUp() {
        profiles = new ArrayList<>();

        UserProfile userProfile = new UserProfile();
        userProfile.setId("1");
        userProfile.setEmail("user@example.com");
        userProfile.setName("User");
        userProfile.setSurname("Test");
        userProfile.setRole("UTENTE");
        userProfile.setBirthDate(LocalDateTime.now());
        userProfile.setCardCvv("123");
        userProfile.setCardExpiryDate("12/23");
        userProfile.setCardNumber("1234567890123456");
        userProfile.setFiscalCode("ABCDEF12G34H567I");
        userProfile.setResidenceAddress("Via Roma 1");
        userProfile.setResidenceCity("Lecce");
        userProfile.setRegistrationDate(LocalDateTime.now());
        userProfile.setPhoneNumber("1234567890");
        userProfile.setEnabled(true);

        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setId("2");
        memberProfile.setEmail("member@example.com");
        memberProfile.setName("Member");
        memberProfile.setSurname("Test");
        memberProfile.setRole("MEMBRO");
        memberProfile.setEnabled(true);

        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setId("3");
        adminProfile.setEmail("admin@example.com");
        adminProfile.setName("Admin");
        adminProfile.setSurname("Test");
        adminProfile.setRole("ADMIN");
        adminProfile.setEnabled(true);

        profiles.add(userProfile);
        profiles.add(memberProfile);
        profiles.add(adminProfile);
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "UTENTE", "MEMBRO"})
    void getAllProfiles_shouldReturnProfileList() throws Exception {
        when(profileRepository.findAll()).thenReturn(profiles);

        mockMvc.perform(get("/api/profile/find/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profilesList", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "UTENTE", "MEMBRO"})
    void getProfileWithFilters_shouldReturnProfileList() throws Exception {
        ProfileQueryFilters filters = new ProfileQueryFilters();
        when(profileService.findProfiles(filters)).thenReturn(profiles);

        mockMvc.perform(get("/api/profile/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("profileQueryFilters", filters))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profilesList", hasSize(3)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "UTENTE", "MEMBRO"})
    void updateProfile_shouldUpdateAndReturnProfile() throws Exception {
        UserProfile userProfile = (UserProfile) profiles.getFirst();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setEmail("user@example.com");
        userProfileDTO.setName("User");
        userProfileDTO.setSurname("Test");
        userProfileDTO.setRole("UTENTE");
        userProfileDTO.setCardCvv("123");
        userProfileDTO.setCardExpiryDate("12/23");
        userProfileDTO.setCardNumber("1234567890123456");
        userProfileDTO.setFiscalCode("ABCDEF12G34H567I");
        userProfileDTO.setResidenceAddress("Via Roma 1");
        userProfileDTO.setResidenceCity("Lecce");
        userProfileDTO.setPhoneNumber("1234567890");
        userProfileDTO.setEnabled(true);

        when(profileRepository.findByEmail(userProfileDTO.getEmail())).thenReturn(Optional.of(userProfile));
        when(profileService.updateProfile(any(), any())).thenReturn(userProfile);
        when(profileService.updateUserProfile(any(), any())).thenReturn(userProfile);
        when(profileRepository.save(userProfile)).thenReturn(userProfile);
        when(profileService.getUserProfileDTO(any())).thenReturn(userProfileDTO);

        // Convert UserProfileDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUserProfileDTO = objectMapper.writeValueAsString(userProfileDTO);

        mockMvc.perform(put("/api/profile/updateProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserProfileDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userProfile.getEmail())))
                .andExpect(jsonPath("$.name", is(userProfile.getName())))
                .andExpect(jsonPath("$.surname", is(userProfile.getSurname())))
                .andExpect(jsonPath("$.role", is(userProfile.getRole())))
                .andExpect(jsonPath("$.cardCvv", is(userProfile.getCardCvv())))
                .andExpect(jsonPath("$.cardExpiryDate", is(userProfile.getCardExpiryDate())))
                .andExpect(jsonPath("$.cardNumber", is(userProfile.getCardNumber())))
                .andExpect(jsonPath("$.fiscalCode", is(userProfile.getFiscalCode())))
                .andExpect(jsonPath("$.residenceAddress", is(userProfile.getResidenceAddress())))
                .andExpect(jsonPath("$.residenceCity", is(userProfile.getResidenceCity())))
                .andExpect(jsonPath("$.phoneNumber", is(userProfile.getPhoneNumber())))
                .andExpect(jsonPath("$.enabled", is(userProfile.isEnabled())));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "UTENTE", "MEMBRO"})
    void updateProfile_shouldReturnProfileNotFoundException_whenEmailIsMissing() throws Exception {
        mockMvc.perform(put("/api/profile/updateProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"User\", \"surname\":\"Test\", \"role\":\"UTENTE\", \"enabled\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "UTENTE", "MEMBRO"})
    void updateProfile_shouldReturnProfileNotFoundException_whenProfileNotFound() throws Exception {
        when(profileRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/profile/updateProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"nonexistent@example.com\", \"name\":\"User\", \"surname\":\"Test\", \"role\":\"UTENTE\", \"enabled\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void enableProfile_shouldEnableAndReturnProfile() throws Exception {
        UserProfile userProfile = (UserProfile) profiles.getFirst();
        userProfile.setEnabled(false);

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setEmail("user@example.com");
        userProfileDTO.setName("User");
        userProfileDTO.setSurname("Test");
        userProfileDTO.setEnabled(true);

        when(profileRepository.findByEmail(userProfile.getEmail())).thenReturn(Optional.of(userProfile));
        when(profileRepository.save(userProfile)).thenReturn(userProfile);
        when(profileService.getUserProfileDTO(userProfile)).thenReturn(userProfileDTO);

        mockMvc.perform(put("/api/profile/enable/{profileEmail}", userProfile.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled", is(true)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void enableProfile_shouldReturnProfileNotFoundException_whenProfileNotFound() throws Exception {
        when(profileRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/profile/enable/nonexistent@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void disableProfile_shouldDisableAndReturnProfile() throws Exception {
        UserProfile userProfile = (UserProfile) profiles.getFirst();
        userProfile.setEnabled(true);

        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setEmail(userProfile.getEmail());
        userProfileDTO.setName(userProfile.getName());
        userProfileDTO.setSurname(userProfile.getSurname());
        userProfileDTO.setEnabled(false);

        when(profileRepository.findByEmail(userProfile.getEmail())).thenReturn(Optional.of(userProfile));
        when(profileRepository.save(userProfile)).thenReturn(userProfile);
        when(profileService.getUserProfileDTO(userProfile)).thenReturn(userProfileDTO);

        mockMvc.perform(put("/api/profile/disable/{profileEmail}", userProfile.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled", is(false)));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void disableProfile_shouldReturnProfileNotFoundException_whenProfileNotFound() throws Exception {
        when(profileRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/profile/disable/nonexistent@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
