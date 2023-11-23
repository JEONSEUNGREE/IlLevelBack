package com.trip.penguin.security.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String userEmail;

    private String userPwd;
}