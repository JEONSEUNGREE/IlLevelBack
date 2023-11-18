package com.trip.penguin.review.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.review.repository.ReviewMsRepository;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.user.domain.UserMS;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private ReviewMsRepository reviewMsRepository;

	@Autowired
	public ReviewServiceImpl(ReviewMsRepository reviewMsRepository) {
		this.reviewMsRepository = reviewMsRepository;
	}

	@Override
	public ReviewMS createReviewMs(ReviewMS reviewMS, BookingMS bookingMS, UserMS userMS) {

		reviewMS.createReview(bookingMS, userMS);

		return reviewMsRepository.save(reviewMS);
	}

	@Override
	public Optional<ReviewMS> getReviewMs(ReviewMS reviewMS) {

		return reviewMsRepository.findById(reviewMS.getId());
	}

	/**
	 * 객실에 해당 되는 리뷰 목록
	 * @param roomMS - 객실 정보
	 * @return List<ReviewMS>
	 */
	@Override
	public List<ReviewMS> getReviewListUsingRoomJoin(RoomMS roomMS) {

		return reviewMsRepository.getReviewListUsingRoomJoin(roomMS.getId()).get(0).getReviewList();
	}

	@Override
	public ReviewMS updateReviewMs(ReviewMS reviewMS) {
		return null;
	}

	@Override
	public void deleteReviewMs(ReviewMS reviewMS) {

	}
}
