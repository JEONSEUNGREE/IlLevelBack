package com.trip.penguin.review.service;

import java.util.List;
import java.util.Optional;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.user.domain.UserMS;

public interface ReviewService {

	public ReviewMS createReviewMs(ReviewMS reviewMS, BookingMS bookingMS, UserMS userMS);

	public Optional<ReviewMS> getReviewMs(ReviewMS reviewMS);

	public List<ReviewMS> getReviewListUsingRoomJoin(RoomMS roomMS);

	public ReviewMS updateReviewMs(ReviewMS reviewMS);

	public void deleteReviewMs(ReviewMS reviewMS);

}
