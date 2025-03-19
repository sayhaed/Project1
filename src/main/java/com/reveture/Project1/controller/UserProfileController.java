package com.reveture.Project1.controller;

import com.reveture.Project1.dto.UserProfileDTO;
import com.reveture.Project1.entity.UserProfile;
import com.reveture.Project1.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@Valid @RequestBody UserProfileDTO userProfileDTO){
       UserProfile userProfile = userProfileService.addUserDetails(userProfileDTO);
        return ResponseEntity.ok(userProfile);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.getUserByUserProfileId(id));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<UserProfile> getUserProfileByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(userProfileService.getUserByAccountId(accountId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
    }
}
