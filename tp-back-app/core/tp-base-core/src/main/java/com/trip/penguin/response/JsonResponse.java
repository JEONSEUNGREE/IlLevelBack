package com.trip.penguin.response;

import com.trip.penguin.constant.CommonConstant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JsonResponse<T> {

	private final String message;

	private final CommonConstant result;

	private final T data;

	public static <T> JsonResponse<T> success(T data) {
		return new JsonResponse<>(CommonConstant.SUCCESS.name(), CommonConstant.SUCCESS, data);
	}

	public static <T> JsonResponse<T> success() {
		return new JsonResponse<>(CommonConstant.SUCCESS.name(), CommonConstant.SUCCESS, null);
	}
}
