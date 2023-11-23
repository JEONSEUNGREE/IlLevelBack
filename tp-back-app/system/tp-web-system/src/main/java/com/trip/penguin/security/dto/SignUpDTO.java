package com.trip.penguin.security.dto;

import lombok.Data;

@Data
public class SignUpDTO {

    private String userCity;

    private String userEmail;

    private String userPwd;

    private String userFirst;

    private String userLast;

    private String userNick;
}
