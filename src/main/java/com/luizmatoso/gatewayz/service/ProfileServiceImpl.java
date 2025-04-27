package com.luizmatoso.gatewayz.service;

import com.luizmatoso.gatewayz.dto.ProfileRequest;
import com.luizmatoso.gatewayz.dto.ProfileResponse;
import com.luizmatoso.gatewayz.entity.User;
import com.luizmatoso.gatewayz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        User newUserProfile = convertToUserEntity(request);
        if (!userRepository.existsByEmail(request.getEmail())){
            newUserProfile = userRepository.save(newUserProfile);
            return convertToProfileResponse(newUserProfile);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists.");


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
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }
}
