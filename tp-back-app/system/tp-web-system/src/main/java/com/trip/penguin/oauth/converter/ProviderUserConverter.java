package com.trip.penguin.oauth.converter;

public interface ProviderUserConverter<T, R> {
	R converter(T t);
}
