package com.example.profilemodule.web.service.interfaces;

import com.example.profilemodule.domain.dto.ProfileRequest;
import com.example.profilemodule.domain.dto.ProfileRequestQueue;
import com.example.profilemodule.domain.entity.Profile;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProfileService {
    String updateProfile(ProfileRequest profileRequest,Long userid);
    @RabbitListener(queues = "PROFILE_ACCESS")
     void listeners(ProfileRequestQueue message);

     Profile getProfile(Long identityNo);

}
