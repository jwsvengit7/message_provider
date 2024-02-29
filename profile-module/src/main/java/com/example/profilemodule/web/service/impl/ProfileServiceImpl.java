package com.example.profilemodule.web.service.impl;

import com.example.profilemodule.domain.dto.ProfileRequest;
import com.example.profilemodule.domain.dto.ProfileRequestQueue;
import com.example.profilemodule.domain.dto.response.BaseResponse;
import com.example.profilemodule.domain.entity.AccountDetails;
import com.example.profilemodule.domain.entity.Profile;
import com.example.profilemodule.domain.enums.Roles;
import com.example.profilemodule.domain.repository.ProfileRepository;
import com.example.profilemodule.security.SecurityFilter;
import com.example.profilemodule.web.service.interfaces.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.profilemodule.utils.Utils.PROFILE_QUEUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final SecurityFilter securityFilter;
    private final CacheService cacheService;

    @Override
    @RabbitListener(queues = PROFILE_QUEUE)
    public void listeners(ProfileRequestQueue message) {
        log.info("{}",message.toString());
        queueForProfile(message);
    }

    private void queueForProfile(ProfileRequestQueue profileRequestQueue){
        String[] fullName = profileRequestQueue.getFullname().split(" ");

        HashMap<String,String> userFullName = new HashMap<>();
        if (fullName.length > 0) {
            userFullName.put("Firstname",fullName[0]);
            userFullName.put("LastName",fullName[fullName.length - 1]);
        }
        Profile profile = save(profileRequestQueue,userFullName);
        profileRepository.insert(profile);
        log.info("************** SAVING INTO PROFILE  ************");


    }
    private Profile save(ProfileRequestQueue profileRequestQueue,HashMap<String,String> userFullName){
        return  Profile.builder()
                .email(profileRequestQueue.getEmail())
                .firstName(userFullName.get("Firstname"))
                .lastName(userFullName.get("LastName"))
                .roles(Roles.valueOf(profileRequestQueue.getRole()))
                .identityNo(profileRequestQueue.getId())
                .build();
    }
    @Override
    public BaseResponse<?> updateProfile(ProfileRequest profileRequest, Long userId, HttpServletRequest request){
        BaseResponse<Object> baseResponse =baseResponse();

        if(validateRequestFromHeaders(request)) {
            if (!Objects.nonNull(profileRequest)) {
                baseResponse.setData("Error Try again");
                baseResponse.setStatusCode(-999);
                return baseResponse;
            }
            Profile profile = profileRepository.findByEmailAndIdentityNo(profileRequest.getEmail(),userId).get();
            if (!Objects.nonNull(profile)){
                baseResponse.setData("User Not found");
                baseResponse.setStatusCode(-909);
                return baseResponse;
            }

            profile.setFirstName(profileRequest.getFirstName());
            profile.setLastName(profileRequest.getLastname());
            profile.setIdentityNo(userId);
            profile.setDob(profileRequest.getDob());
            profile.setBvn(profileRequest.getBvn());
            List<AccountDetails> accountDetailsList = profileRequest.getAccountDetailsList()
                    .stream()
                    .map(account -> AccountDetails.builder()
                            .id(profile.getId())
                            .accountName(account.getAccountName())
                            .accountNumber(account.getAccountNumber())
                            .bankName(account.getBankName()).build())
                    .collect(Collectors.toList());
            profile.setAccountDetails(accountDetailsList);
            log.info("************** UPDATE PROFILE  ************");

            profileRepository.save(profile);

            log.info("************** CACHE EVICTING FOR USER  ************");

            cacheService.invalidateProfileCache(profile.getIdentityNo());
            baseResponse.setData("update successfully");
            baseResponse.setStatusCode(101);
            return baseResponse;
        }else{
            baseResponse.setData("Exception ");
            baseResponse.setStatusCode(-999);
            return baseResponse;
        }
    }

    @Cacheable(key = "#identityNo",value = "Profile")
    public BaseResponse<Object> getProfile(Long identityNo,HttpServletRequest httpServletRequest){
        BaseResponse<Object> baseResponse = baseResponse();
        if(validateRequestFromHeaders(httpServletRequest)) {
            Profile profile = profileRepository.findByIdentityNo(identityNo)
                    .orElseThrow(() -> new RuntimeException("User Not found"));
            if (!Objects.nonNull(profile)) {
                throw new RuntimeException("Object cant be null");
            }
            baseResponse.setData(profile);
            baseResponse.setStatusCode(-999);
            return baseResponse;

        }else{
            baseResponse.setData("Error occurred");
            baseResponse.setStatusCode(-999);
            return baseResponse;
        }
    }

    private Boolean validateRequestFromHeaders(HttpServletRequest request){
        return securityFilter.filterSecurity(request);
    }

    private BaseResponse<Object> baseResponse(){
        return new BaseResponse<>();
    }


}
