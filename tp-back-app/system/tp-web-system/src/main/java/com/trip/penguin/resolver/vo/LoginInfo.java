package com.trip.penguin.resolver.vo;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class LoginInfo {

    public String userEmail;

    public String jwtToken;

}
