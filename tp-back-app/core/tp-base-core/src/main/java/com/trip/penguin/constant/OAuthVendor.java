package com.trip.penguin.constant;

public enum OAuthVendor {
	GOOGLE("GOOGLE"),
	NAVER("NAVER"),
	KAKAO("KAKAO"),
	;

	private final String socialName;

	OAuthVendor(String socialName) {
		this.socialName = socialName;
	}

	public String getOAuthVendor() {
		return socialName;
	}
}
