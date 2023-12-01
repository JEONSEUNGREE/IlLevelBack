package com.trip.penguin.resolver.vo;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class LoginUserInfo {

    private String userEmail;

    private String role;

    private String jwtToken;

}
