package com.trip.penguin.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.booking.repository.BookingMSRepository;
import com.trip.penguin.user.domain.UserMS;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class BookingMsServiceImpl implements BookingMsService {

	private BookingMSRepository bookingMSRepository;

	@Autowired
	public BookingMsServiceImpl(BookingMSRepository bookingMSRepository) {
		this.bookingMSRepository = bookingMSRepository;
	}

	@Override
	public BookingMS createBookingMs(BookingMS bookingMS, UserMS userMS) {

		bookingMS.createBookingMs();

		return bookingMSRepository.save(bookingMS);
	}
}
