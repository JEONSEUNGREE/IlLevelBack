package com.trip.penguin.user.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMyPageView {

	@NonNull
	private String userLast;

	@NonNull
	private String userFirst;

	@NonNull
	private String userNick;

	@NonNull
	private String userCity;

}
