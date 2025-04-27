package com.luizmatoso.gatewayz.service;

import com.luizmatoso.gatewayz.dto.ProfileRequest;
import com.luizmatoso.gatewayz.dto.ProfileResponse;
import com.luizmatoso.gatewayz.entity.User;
import com.luizmatoso.gatewayz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        User newUserProfile = convertToUserEntity(request);
        newUserProfile = userRepository.save(newUserProfile);
        return convertToProfileResponse(newUserProfile);
    }

    private ProfileResponse convertToProfileResponse(User newUserProfile) {
        return ProfileResponse.builder()
                .name(newUserProfile.getName())
                .email(newUserProfile.getEmail())
                .userId(newUserProfile.getUserId())
                .isAccountVerified(newUserProfile.getIsAccountVerified())
                .build();
    }

    private User convertToUserEntity(ProfileRequest request) {
        return User.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(request.getPassword())
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
