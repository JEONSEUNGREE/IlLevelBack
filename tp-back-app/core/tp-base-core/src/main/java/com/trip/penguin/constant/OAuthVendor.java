package com.trip.penguin.constant;

public enum OAuthVendor {
	GOOGLE("google"),
	NAVER("naver"),
	KAKAO("kakao"),
	;

	private final String socialName;

	OAuthVendor(String socialName) {
		this.socialName = socialName;
	}

	public String getOAuthVendor() {
		return socialName;
	}
}
