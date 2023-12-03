package com.example.profilemodule.web.comtrollers;


import com.example.profilemodule.domain.dto.ProfileRequest;
import com.example.profilemodule.domain.entity.Profile;
import com.example.profilemodule.web.service.interfaces.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PutMapping("update/{userId}")
    public ResponseEntity<String> updateProfile(@Validated @RequestBody ProfileRequest profileRequest, @PathVariable("userId")  Long userId){
        return new ResponseEntity<>(profileService.updateProfile(profileRequest,userId), HttpStatus.OK);
    }


    @GetMapping("/{identityNo}")
    public ResponseEntity<Profile> updateProfile(@Validated @PathVariable("identityNo")  Long identityNo){
        return new ResponseEntity<>(profileService.getProfile(identityNo), HttpStatus.OK);
    }

}
