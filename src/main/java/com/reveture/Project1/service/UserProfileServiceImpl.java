package com.reveture.Project1.service;

import com.reveture.Project1.dto.UserProfileDTO;
import com.reveture.Project1.entity.Account;
import com.reveture.Project1.entity.UserProfile;
import com.reveture.Project1.repository.AccountRepository;
import com.reveture.Project1.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserProfile addUserDetails(UserProfileDTO userProfileDTO) {
        //validar si ya existe el usuario
        Account account = accountRepository.findById(userProfileDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + userProfileDTO.getAccountId()));

        if (userProfileRepository.existsByAccount(account)) {
            throw new RuntimeException("UserProfile already exists for Account ID: " + userProfileDTO.getAccountId());
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setAccount(account);
        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        userProfile.setCreditScore(userProfileDTO.getCreditScore());

        return userProfileRepository.save(userProfile);
    }

    @Override
    public List<UserProfile> getAllUserDetails() { return userProfileRepository.findAll(); }

    @Override
    public UserProfile getUserByUserProfileId(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Profile not found with ID: " + id));
    }

    @Override
    public UserProfile getUserByAccountId(Long id) {
        return userProfileRepository.findByAccountId(id)
                .orElseThrow(() -> new RuntimeException("User profile not found for account ID: " + id));
    }

    @Override
    public UserProfile updateUserProfile(Long id, UserProfileDTO userProfileDTO) {
        UserProfile userProfile = getUserByUserProfileId(id);

        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfile.setPhoneNumber(userProfileDTO.getPhoneNumber());
        userProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        userProfile.setCreditScore(userProfileDTO.getCreditScore());

        return userProfileRepository.save(userProfile);
    }

}
