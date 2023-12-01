package com.trip.penguin.resolver.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginCompanyInfo {

    private String comEmail;

    private String role;

    private String jwtToken;
}
