package com.trip.penguin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserMyPageDTO {

	@NonNull
	private String userLast;

	@NonNull
	private String userFirst;

	@NonNull
	private String userNick;

	@NonNull
	private String userImg;

	@NonNull
	private String userCity;

}
