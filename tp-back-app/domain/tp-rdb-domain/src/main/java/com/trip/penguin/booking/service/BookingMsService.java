package com.trip.penguin.booking.service;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.user.domain.UserMS;

public interface BookingMsService {

	public BookingMS createBookingMs(BookingMS bookingMS, UserMS userMS);

}
