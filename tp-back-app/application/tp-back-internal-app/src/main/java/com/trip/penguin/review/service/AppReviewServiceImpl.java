package com.trip.penguin.review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.booking.service.BookingMsService;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.review.dto.AppReviewDTO;
import com.trip.penguin.review.view.AppReviewView;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

@Service
public class AppReviewServiceImpl implements AppReviewService {

	private ReviewService reviewService;

	private UserService userService;

	private BookingMsService bookingMsService;

	@Autowired
	public AppReviewServiceImpl(ReviewService reviewService, UserService userService,
		BookingMsService bookingMsService) {
		this.reviewService = reviewService;
		this.userService = userService;
		this.bookingMsService = bookingMsService;
	}

	@Override
	public AppReviewDTO reviewCreate(AppReviewView appReviewView, LoginUserInfo loginUserInfo) {

		UserMS foundUser = userService.getUserByUserEmail(loginUserInfo.getUserEmail())
			.orElseThrow(UserNotFoundException::new);

		/* 구매 유저만 리뷰 가능 구매 코드 확인은 추후*/
		BookingMS bookingMS = bookingMsService.getBookingByIdAndUserMs(appReviewView.getBookingId(), foundUser)
			.orElseThrow();

		/* 리뷰 생성 */
		ReviewMS review = reviewService.createReviewMs(
			ReviewMS.builder()
				.reTitle(appReviewView.getReTitle())
				.reContent(appReviewView.getReContent())
				.rating(appReviewView.getRating())
				.build(),
			bookingMS,
			foundUser
		);

		return new AppReviewDTO().changeDTO(review);
	}
}
