package com.example.profilemodule.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheService {

    @CacheEvict(key = "#identityNo", value = "Profile")
    public void invalidateProfileCache(Long identityNo) {
        log.info("******** cache invalidated for identityNo: "+identityNo+" ********") ;
    }




}
