package com.trip.penguin.user.view;

import java.time.LocalDateTime;

import com.trip.penguin.user.domain.UserMS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserMyPageProfileDTO {

	private String userEmail;

	private String userNick;

	private String userImg;

	private String city;

	private String socialProvider;

	private LocalDateTime createdDate;

	private String userFirst;

	private String userLast;

	private Integer followerCnt;

	private Integer followCnt;

	private String introduce;

	public UserMyPageProfileDTO changeDTO(UserMS userMS, Integer followCnt, Integer followerCnt) {

		this.userEmail = userMS.getUserEmail();
		this.userNick = userMS.getUserNick();
		this.userImg = userMS.getUserImg();
		this.city = userMS.getUserCity();
		this.socialProvider = userMS.getSocialProvider();
		this.createdDate = userMS.getCreatedDate();
		this.userFirst = userMS.getUserFirst();
		this.userLast = userMS.getUserLast();
		this.introduce = userMS.getIntroduce();
		this.followCnt = followCnt;
		this.followerCnt = followerCnt;

		return this;
	}

}
