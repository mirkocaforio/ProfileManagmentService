package it.unisalento.pasproject.profilemanagmentservice.service;

import it.unisalento.pasproject.profilemanagmentservice.business.producer.MessageProducer;
import it.unisalento.pasproject.profilemanagmentservice.domain.AdminProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.MemberProfile;
import it.unisalento.pasproject.profilemanagmentservice.domain.UserProfile;
import it.unisalento.pasproject.profilemanagmentservice.dto.RegistrationDTO;
import it.unisalento.pasproject.profilemanagmentservice.dto.UpdatedProfileMessageDTO;
import it.unisalento.pasproject.profilemanagmentservice.repositories.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProfileMessageHandler {
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final MessageProducer messageProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileMessageHandler.class);

    @Value("${rabbitmq.routing.update.key}")
    private String updateProfileTopic;

    @Value("${rabbitmq.exchange.update.name}")
    private String updateDataExchange;

    @Autowired
    public ProfileMessageHandler(ProfileRepository profileRepository, MessageProducer messageProducer, ProfileService profileService) {
        this.profileRepository = profileRepository;
        this.messageProducer = messageProducer;
        this.profileService = profileService;
    }

    public void sendProfileMessage(UpdatedProfileMessageDTO updatedProfileMessageDTO) {
        messageProducer.sendMessage(updatedProfileMessageDTO, updateProfileTopic, updateDataExchange);
        LOGGER.info("Send message: {}", updatedProfileMessageDTO.toString());
    }

    @RabbitListener(queues = "${rabbitmq.queue.data.name}")
    public void receiveRegistrationInfoMessage(RegistrationDTO registrationDTO) {
        try {
            LOGGER.info("Received message: {}", registrationDTO.toString());
            switch (registrationDTO.getRole()) {
                case "UTENTE":
                    UserProfile userProfile = profileService.getUserProfile(registrationDTO);
                    profileRepository.save(userProfile);
                    LOGGER.info("UserProfile: {}", userProfile.getId());
                    break;
                case "MEMBRO":
                    MemberProfile memberProfile = profileService.getMemberProfile(registrationDTO);
                    profileRepository.save(memberProfile);
                    LOGGER.info("MemberProfile: {}", memberProfile.getId());
                    break;
                case "ADMIN":
                    AdminProfile adminProfile = profileService.getAdminProfile(registrationDTO);
                    profileRepository.save(adminProfile);
                    LOGGER.info("AdminProfile: {}", adminProfile.getId());
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage());
        }
    }
}
