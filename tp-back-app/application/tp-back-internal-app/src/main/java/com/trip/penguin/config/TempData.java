package com.trip.penguin.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.security.encoder.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.booking.service.BookingMsService;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.review.service.ReviewService;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.repository.RoomMSRepository;
import com.trip.penguin.room.service.RoomService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TempData implements CommandLineRunner {

	private final CompanyService companyService;

	private final UserService userService;

	private final RoomService roomService;

	private final BookingMsService bookingMsService;

	private final ReviewService reviewService;

	private final RoomMSRepository roomMSRepository;

	@Override
	public void run(String... args) throws Exception {

		if (roomMSRepository.findAll().isEmpty()) {

			CompanyMS beforeCommitCompany;

			UserMS beforeCommitUser;

			List<BookingMS> beforeCommitBookingMs = new ArrayList<>();

			List<RoomMS> beforeCommitRoomMSList = new ArrayList<>();

			List<ReviewMS> reviewList = new ArrayList<>();

			/* 회사 가입 */
			beforeCommitCompany = CompanyMS.builder()
				.com_nm("testNm")
				.comEmail("test@test.com")
				.comPwd("testPwd")
				.comImg("defaultImg")
				.comAddress("location")
				.comApproval(CommonConstant.N.name())
				.userRole(CommonUserRole.ROLE_COM)
				.build();

			/* 객실 등록 */
			for (int i = 1; i <= 10; i++) {
				beforeCommitRoomMSList.add(RoomMS.builder()
					.roomNm("testNm" + i)
					.checkIn(LocalDateTime.now())
					.checkOut(LocalDateTime.now())
					.couponYn(CommonConstant.Y.name())
					.thumbNail("http://tp-penguin-app.store/tp-penguin-app/image/user/default.jpg")
					.sellPrc(120000)
					.maxCount(5)
					.soldOutYn(CommonConstant.N.name())
					.roomDesc("Desc")
					.build());
			}

			/* 회원 가입 정보 */
			beforeCommitUser = UserMS.builder()
					.offYn("N")
					.userCity("Seoul")
					.userImg("default")
					.userEmail("test@email.com")
					.userRole("user")
					.userNick("default")
					.userPwd("test")
					.userFirst("t")
					.userLast("est")
					.socialProvider(OAuthVendor.DEFAULT.getOAuthVendor())
					.createdDate(LocalDateTime.now())
					.modifiedDate(LocalDateTime.now())
					.build();

			/* 예약 등록 */
			for (int i = 0; i < 3; i++) {
				beforeCommitBookingMs.add(BookingMS.builder()
					.room(beforeCommitRoomMSList.get(i))
					.bookNm("test" + i)
					.userMS(beforeCommitUser)
					.couponYn(CommonConstant.Y.name())
					.sellPrc(500000)
					.payMethod("payMethod")
					.checkIn(LocalDateTime.now())
					.checkOut(LocalDateTime.now())
					.payAmount(500000)
					.build());
			}

			/* 리뷰 4개 생성 */
			for (int i = 1; i <= 4; i++) {
				reviewList.add(ReviewMS.builder()
					.reTitle("title" + i)
					.reContent("content" + i)
					.rating(i)
					.reAccom("no reply" + i)
					.report(CommonConstant.N.name())
					.build());
			}

			userService.signUpUser(beforeCommitUser);

			companyService.createCompany(beforeCommitCompany);

			for (int i = 0; i < beforeCommitRoomMSList.size(); i++) {
				roomService.createRoom(beforeCommitRoomMSList.get(i), beforeCommitCompany, "", new ArrayList<>());
			}

			/* 예약 등록 */
			bookingMsService.createBookingMs(beforeCommitBookingMs.get(0), beforeCommitUser);
			bookingMsService.createBookingMs(beforeCommitBookingMs.get(1), beforeCommitUser);
			bookingMsService.createBookingMs(beforeCommitBookingMs.get(2), beforeCommitUser);

			/* 리뷰 2개씩 등록 */
			// 첫번째 객실에 리뷰 등록 2개 등록
			reviewService.createReviewMs(reviewList.get(0), beforeCommitBookingMs.get(0), beforeCommitUser);
			reviewService.createReviewMs(reviewList.get(1), beforeCommitBookingMs.get(0), beforeCommitUser);

			// 두번째 객실에 리뷰 등록 2개 등록
			reviewService.createReviewMs(reviewList.get(2), beforeCommitBookingMs.get(1), beforeCommitUser);
			reviewService.createReviewMs(reviewList.get(3), beforeCommitBookingMs.get(1), beforeCommitUser);
		}
	}
}
