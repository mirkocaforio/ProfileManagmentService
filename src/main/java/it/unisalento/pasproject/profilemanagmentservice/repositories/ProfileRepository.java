package it.unisalento.pasproject.profilemanagmentservice.repositories;

import it.unisalento.pasproject.profilemanagmentservice.domain.GenericProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<GenericProfile, String> {
}