package com.trip.penguin.resolver.vo;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class LoginInfo {

    public String userId;

    public String jwtToken;

}
