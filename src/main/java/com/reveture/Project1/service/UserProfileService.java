package com.reveture.Project1.service;

import com.reveture.Project1.dto.AccountDTO;
import com.reveture.Project1.dto.UserProfileDTO;
import com.reveture.Project1.entity.UserProfile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserProfileService {

    UserProfile addUserDetails(UserProfileDTO userProfileDTO);
    List<UserProfile> getAllUserDetails();
    UserProfile getUserByUserProfileId(Long id);
    UserProfile getUserByAccountId(Long id);
    UserProfile updateUserProfile(Long id,UserProfileDTO userProfileDTO);

}
