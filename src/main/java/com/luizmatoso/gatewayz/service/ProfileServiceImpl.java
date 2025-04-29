package com.luizmatoso.gatewayz.service;

import com.luizmatoso.gatewayz.dto.ProfileRequest;
import com.luizmatoso.gatewayz.dto.ProfileResponse;
import com.luizmatoso.gatewayz.entity.User;
import com.luizmatoso.gatewayz.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        User newUserProfile = convertToUserEntity(request);
        if (!userRepository.existsByEmail(request.getEmail())){
            newUserProfile = userRepository.save(newUserProfile);
            return convertToProfileResponse(newUserProfile);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists.");
    }

    @Override
    public ProfileResponse getProfile(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return convertToProfileResponse(existingUser);
    }

    @Override
    public void sendResetOtp(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // otp - 6 digits
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        // expiration Time
        long expirationTime = System.currentTimeMillis() + (15 * 60 * 1000);

        // update profile
        existingUser.setResetOtp(otp);
        existingUser.setResetOtpExpireAt(expirationTime);

        //save into database
        userRepository.save(existingUser);

        try{
            emailService.sendResetOtpEmail(existingUser.getEmail(), otp);
        } catch (Exception e){
            throw new RuntimeException("Unable to send email.");
        }
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (existingUser.getResetOtp() == null || !existingUser.getResetOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP.");
        }

        if (existingUser.getResetOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired.");
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        existingUser.setResetOtp(null);
        existingUser.setResetOtpExpireAt(0L);

        userRepository.save(existingUser);
    }

    @Override
    public void sendOtp(String email) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (existingUser.getIsAccountVerified() != null && existingUser.getIsAccountVerified()){
            return;
        }

        // otp - 6 digits
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        // expiration Time
        long expirationTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000);

        // update user
        existingUser.setVerifyOtp(otp);
        existingUser.setVerifyOtpExpireAt(expirationTime);

        // save into database
        userRepository.save(existingUser);

        try{
            emailService.sendOtpEmail(existingUser.getEmail(), otp);
        } catch (Exception e){
            throw new RuntimeException("Unable to send email.");
        }

    }

    @Override
    public void verifyOtp(String email, String otp) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (existingUser.getVerifyOtp() == null || !existingUser.getVerifyOtp().equals(otp)){
            throw new RuntimeException("Invalid OTP.");
        }

        if (existingUser.getVerifyOtpExpireAt() < System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired.");
        }

        existingUser.setIsAccountVerified(true);
        existingUser.setVerifyOtp(null);
        existingUser.setVerifyOtpExpireAt(0L);

        userRepository.save(existingUser);
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
