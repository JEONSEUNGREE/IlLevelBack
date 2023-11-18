package com.trip.penguin.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.penguin.booking.domain.BookingMS;

public interface BookingMSRepository extends JpaRepository<BookingMS, Long> {
}
