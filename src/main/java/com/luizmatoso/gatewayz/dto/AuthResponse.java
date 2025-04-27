package com.luizmatoso.gatewayz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {

    private String email;
    private String jwtToken;

}
