package it.unisalento.pasproject.profilemanagmentservice.repositories;

import it.unisalento.pasproject.profilemanagmentservice.domain.GenericProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<GenericProfile, String> {
    Optional<GenericProfile> findByEmail(String email);
}