package com.trip.penguin.booking.service;

import java.util.Optional;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.user.domain.UserMS;

public interface BookingMsService {

	BookingMS createBookingMs(BookingMS bookingMS, UserMS userMS);

	Optional<BookingMS> getBookingByIdAndUserMs(Long bookingId, UserMS userMS);

}
