package com.luizmatoso.gatewayz.service;

import com.luizmatoso.gatewayz.dto.ProfileRequest;
import com.luizmatoso.gatewayz.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);

    ProfileResponse getProfile(String email);

}
