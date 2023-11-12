package com.example.profilemodule.web.service.impl;

import com.example.profilemodule.domain.dto.ProfileRequestQueue;
import com.example.profilemodule.domain.entity.Profile;
import com.example.profilemodule.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl {

    private final ProfileRepository profileRepository;

    @RabbitListener(queues = "PROFILE_ACCESS")
    public void listeners(ProfileRequestQueue message) {
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



}
