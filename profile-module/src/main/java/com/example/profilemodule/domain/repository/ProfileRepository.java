package com.example.profilemodule.domain.repository;

import com.example.profilemodule.domain.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile,String> {
    Optional<Profile> findByIdentityNo(Long identity);
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByEmailAndIdentityNo(String email,Long identity);


}
