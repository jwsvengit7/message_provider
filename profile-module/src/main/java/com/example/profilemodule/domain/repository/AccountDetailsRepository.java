package com.example.profilemodule.domain.repository;

import com.example.profilemodule.domain.entity.AccountDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountDetailsRepository extends MongoRepository<AccountDetails,String> {
}
