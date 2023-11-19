package com.trip.penguin.review;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.booking.service.BookingMsService;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.config.JpaDataConfig;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.review.service.ReviewService;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.service.RoomService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import jakarta.persistence.EntityManager;

@JpaDataConfig
@DataJpaTest(properties = "classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewCRUDTest {

	private final RoomService roomService;

	private final UserService userService;

	private final CompanyService companyService;

	private final ReviewService reviewService;

	private final BookingMsService bookingMsService;

	private final EntityManager em;

	private RoomMS beforeCommitRoomMS;

	private CompanyMS beforeCommitCompany;

	private UserMS beforeCommitUser;

	private BookingMS beforeCommitBookingMs;

	@Autowired
	public ReviewCRUDTest(RoomService roomService, CompanyService companyService, ReviewService reviewService,
		BookingMsService bookingMsService, UserService userService, EntityManager em) {
		this.roomService = roomService;
		this.companyService = companyService;
		this.reviewService = reviewService;
		this.bookingMsService = bookingMsService;
		this.userService = userService;
		this.em = em;

	}

	@BeforeEach
	public void beforeTest() {
		/* 회사 가입 */
		beforeCommitCompany = CompanyMS.builder()
			.com_nm("testNm")
			.comEmail("test@test.com")
			.comPwd("testPwd")
			.comImg("defaultImg")
			.comAddress("location")
			.comApproval(CommonConstant.N.name())
			.userRole("ROLE_COM")
			.build();

		/* 객실 등록 */
		beforeCommitRoomMS = RoomMS.builder()
			.roomNm("testNm")
			.checkIn(LocalDateTime.now())
			.checkOut(LocalDateTime.now())
			.couponYn(CommonConstant.Y.name())
			.sellPrc(120000)
			.thubNail("default")
			.maxCount(5)
			.soldOutYn(CommonConstant.N.name())
			.roomDesc("Desc")
			.build();

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
			.createdDate(LocalDateTime.now())
			.modifiedDate(LocalDateTime.now())
			.build();

		/* 객실 등록 */
		beforeCommitBookingMs = BookingMS.builder()
			.room(beforeCommitRoomMS)
			.bookNm("test")
			.userMS(beforeCommitUser)
			.couponYn(CommonConstant.Y.name())
			.sellPrc(500000)
			.payMethod("payMethod")
			.checkIn(LocalDateTime.now())
			.checkOut(LocalDateTime.now())
			.payAmount(500000)
			.build();
	}

	@Test
	@DisplayName("리뷰 생성, 조회 테스트")
	void createRoomTest() {

		// given

		/* 리뷰 2개 등록 */
		List<ReviewMS> reviewList = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			reviewList.add(ReviewMS.builder()
				.reTitle("title" + i)
				.reContent("content" + i)
				.rating(5)
				.reAccom("no reply" + i)
				.report(CommonConstant.N.name())
				.build());
		}

		/* 유저 가입 */
		userService.signUpUser(beforeCommitUser);

		/* 회사 가입 */
		companyService.createCompany(beforeCommitCompany);

		/* 객실 생성 */
		RoomMS afterCommitRoomMs = roomService.createRoom(beforeCommitRoomMS, beforeCommitCompany, new ArrayList<>());

		/* 예약 등록 */
		bookingMsService.createBookingMs(beforeCommitBookingMs, beforeCommitUser);

		/* 리뷰 2개 등록 */
		reviewService.createReviewMs(reviewList.get(0), beforeCommitBookingMs, beforeCommitUser);
		reviewService.createReviewMs(reviewList.get(1), beforeCommitBookingMs, beforeCommitUser);

		em.flush();
		em.clear();

		// when
		/* 리뷰 조회
		JPA는 oneToOne관계에서 주인 객체는 Lazy가 적용 되지만 참조객체는 Lazy적용이 안된다 따라서 추가로 fetch join이 필요하다.
		oneToMany + oneToMany가 아니여서 조회 가능
		*/
		List<ReviewMS> afterCommitReviewList = reviewService.getReviewListUsingRoomJoin(afterCommitRoomMs);

		// then
		assertEquals(afterCommitReviewList.size(), 2);
		assertEquals(afterCommitReviewList.get(0).getReTitle(), reviewList.get(0).getReTitle());
		assertEquals(afterCommitReviewList.get(1).getReTitle(), reviewList.get(1).getReTitle());
	}
}
