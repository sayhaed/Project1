package com.reveture.Project1.controller;

import com.reveture.Project1.dto.UserProfileDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.UserProfile;
import com.reveture.Project1.service.AccountService;
import com.reveture.Project1.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private AccountService accountService;

    private Account getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountService.getAccountByEmail(email);
    }

    private boolean isAdmin() {
        return "Admin".equalsIgnoreCase(getCurrentUser().getAccountType().getType());
    }

    //validamos el acceso de admin a este endpoint
    @GetMapping()
    public ResponseEntity<List<UserProfile>> getUserProfiles(@PathVariable Long id) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userProfileService.getAllUserDetails());
    }



    /// validamos el acceso de admin a este endpoint
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getUserProfileById(@PathVariable Long id) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userProfileService.getUserByUserProfileId(id));
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@Valid @RequestBody UserProfileDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.addUserDetails(userProfileDTO));
    }



//    @GetMapping("/account/{accountId}")
//    public ResponseEntity<UserProfile> getUserProfileByAccountId(@PathVariable Long accountId) {
//        return ResponseEntity.ok(userProfileService.getUserByAccountId(accountId));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileDTO userProfileDTO) {
//        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
//    }

    //  Obtener perfil por account id  (admin o dueño)
    /// validamos el acceso de admin a este endpoint
    @GetMapping("/account/{accountId}")
    public ResponseEntity<UserProfile> getUserProfileByAccountId(@PathVariable Long accountId) {
        Account current = getCurrentUser();
        if (!isAdmin() && !current.getId().equals(accountId)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(userProfileService.getUserByAccountId(accountId));
    }

    //usuario logueado actualmente
    @GetMapping("/me")
    public ResponseEntity<UserProfile> getMyUserProfile() {
        Account current = getCurrentUser();
        return ResponseEntity.ok(userProfileService.getUserByAccountId(current.getId()));
    }

    //usuario logueado actualmente
    @PutMapping("/me")
    public ResponseEntity<UserProfile> updateMyUserProfile(@Valid @RequestBody UserProfileDTO userProfileDTO) {
        Account current = getCurrentUser();
        UserProfile existingProfile = userProfileService.getUserByAccountId(current.getId());
        return ResponseEntity.ok(userProfileService.updateUserProfile(existingProfile.getId(), userProfileDTO));
    }

    /// validamos el acceso de admin a este endpoint
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable Long id, @Valid @RequestBody UserProfileDTO userProfileDTO) {
        if (!isAdmin()) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(userProfileService.updateUserProfile(id, userProfileDTO));
    }

}
