package com.example.profilemodule.domain.repository;

import com.example.profilemodule.domain.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfileRepository extends MongoRepository<Profile,String> {

}
