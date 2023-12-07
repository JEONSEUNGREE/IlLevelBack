package com.trip.penguin.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.user.domain.UserMS;

public interface BookingMSRepository extends JpaRepository<BookingMS, Long> {

	Optional<BookingMS> findByUserMSAndId(UserMS userMS, Long id);
}
