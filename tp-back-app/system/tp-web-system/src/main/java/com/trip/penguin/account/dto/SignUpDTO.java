package com.trip.penguin.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

	private String userCity;

	private String userEmail;

	private String userPwd;

	private String userFirst;

	private String userLast;

	private String userNick;
}
