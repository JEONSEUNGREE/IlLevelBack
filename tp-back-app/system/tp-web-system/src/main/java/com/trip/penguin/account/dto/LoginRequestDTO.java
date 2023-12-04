package com.trip.penguin.account.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String userEmail;

    private String userPwd;

    private String type;
}