package com.example.profilemodule.web.service.impl;

import com.example.profilemodule.domain.dto.ProfileRequest;
import com.example.profilemodule.domain.dto.ProfileRequestQueue;
import com.example.profilemodule.domain.entity.AccountDetails;
import com.example.profilemodule.domain.entity.Profile;
import com.example.profilemodule.domain.repository.ProfileRepository;
import com.example.profilemodule.web.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final RedisTemplate<String,Object> redisTemplate;


    @Override
    @RabbitListener(queues = "PROFILE_ACCESS")
    public void listeners(ProfileRequestQueue message) {
//        redisTemplate.opsForHash().put("message",message.getId(),message);
        log.info("{}",message.toString());
        recievedProfileFromqueue(message);
    }


    private void recievedProfileFromqueue(ProfileRequestQueue profileRequestQueue){
        String[] fullname = profileRequestQueue.getFullname().split(" ");
        String firstname = "";
        String lastName = "";
        if (fullname.length > 0) {
            firstname = fullname[0];
            lastName = fullname[fullname.length - 1];
        }
        Profile profile = save(profileRequestQueue,firstname,lastName);
        profileRepository.insert(profile);
        log.info("save into profile");

    }
    private Profile save(ProfileRequestQueue profileRequestQueue,String firstname,String lastName){
        return  Profile.builder()
                .email(profileRequestQueue.getEmail())
                .firstName(firstname)
                .lastName(lastName)
                .identityNo(profileRequestQueue.getId())
                .build();
    }
    @Override
    public String updateProfile(ProfileRequest profileRequest,Long userId){

        Profile profile = profileRepository.findByIdentityNo(userId)
                        .orElseThrow(()-> new RuntimeException("User Not found"));
        if(!Objects.nonNull(profileRequest)){
            throw  new RuntimeException("Object cant be null");
        }
        profile.setFirstName(profileRequest.getFirstName());
        profile.setLastName(profileRequest.getLastname());
        profile.setDob(profileRequest.getDob());
        profile.setBvn(profileRequest.getBvn());
        List<AccountDetails> accountDetailsList = profileRequest.getAccountDetailsList();
        List<AccountDetails> copiedAccountDetails = accountDetailsList.stream()
                .map(account -> {
                    return new AccountDetails(account.getAccountName(),account.getAccountNumber(),account.getBankName()); // Copying the AccountDetails assuming it's a class
                })
                .collect(Collectors.toList());
        profile.setAccountDetails(copiedAccountDetails);

        profileRepository.save(profile);
        return "Successfully Update profile";
    }

    @Cacheable(key = "#identityNo",value = "Profile")
    public Profile getProfile(Long identityNo){
        Profile profile = profileRepository.findByIdentityNo(identityNo)
                .orElseThrow(()-> new RuntimeException("User Not found"));
        if(!Objects.nonNull(profile)){
            throw  new RuntimeException("Object cant be null");
        }
        return profile;
    }
}
