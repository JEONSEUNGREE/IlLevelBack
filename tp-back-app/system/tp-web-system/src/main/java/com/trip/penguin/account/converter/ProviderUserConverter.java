package com.trip.penguin.account.converter;

public interface ProviderUserConverter<T, R> {
	R converter(T t);
}
