package com.trip.penguin.constant;

public enum CommonMessage {

	DUPLICATE_EMAIL("이미 가입된 이메일 형식 입니다"), AVAILABLE_EMAIL("가입 가능한 이메일 형식 입니다"), NOT_ALLOWED_FORMAT_EMAIL(
		"올바른 이메일 형식이 아닙니다");

	private String msg;

	CommonMessage(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}
}
