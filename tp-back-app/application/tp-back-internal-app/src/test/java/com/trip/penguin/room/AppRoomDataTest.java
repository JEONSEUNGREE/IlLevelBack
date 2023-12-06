package com.trip.penguin.room;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.booking.repository.BookingMSRepository;
import com.trip.penguin.booking.service.AppBookingService;
import com.trip.penguin.booking.view.AppBookingView;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.repository.RoomMSRepository;
import com.trip.penguin.room.service.AppRoomService;
import com.trip.penguin.room.view.AppRoomView;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.util.ImgUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
		"com.trip.penguin.user",
		"com.trip.penguin.room",
		"com.trip.penguin.booking",
		"com.trip.penguin.company.service",
		"com.trip.penguin.company.repository",
		"com.trip.penguin.util",
}, excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {UserMyPageController.class, UserMyPageService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class AppRoomDataTest {

	@Autowired
	private UserService userService;

	@Autowired
	private AppRoomService appRoomService;

	@Autowired
	private RoomMSRepository roomMSRepository;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ImgUtils imgUtils;

	@Autowired
	private AppBookingService appBookingService;

	@Autowired
	private EntityManager entityManager;

	private List<UserMS> beforeCommitUserList = new ArrayList<>();

	private CompanyMS beforeCommitCompany;

	@Autowired
	private BookingMSRepository bookingMSRepository;

	@Autowired
	private PlatformTransactionManager tm;
	private TransactionTemplate transaction;
	@BeforeEach
	public void beforeData() {

		transaction = new TransactionTemplate(tm);
		transaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		/* 회원 가입 정보 */
		for (int i = 0; i < 10; i++) {
			beforeCommitUserList.add(
					UserMS.builder()
							.offYn("N")
							.userCity("Seoul" + i)
							.userImg("default" + i)
							.userEmail("test@test.com" + i)
							.userRole("user" + i)
							.userNick("default" + i)
							.userPwd("test" + i)
							.userFirst("t" + i)
							.userLast("est" + i)
							.createdDate(LocalDateTime.now())
							.modifiedDate(LocalDateTime.now())
							.build());
		}

		beforeCommitCompany = CompanyMS.builder()
				.com_nm("testNm")
				.comEmail("test@test.com0")
				.comPwd("testPwd")
				.comImg("defaultImg")
				.comAddress("location")
				.comApproval(CommonConstant.N.name())
				.userRole(CommonUserRole.ROLE_COM)
				.build();
	}

	@DisplayName("객실 생성 등록")
	@Test
	void RoomCreateTest() {

		//given
		LoginCompanyInfo loginUserInfo = LoginCompanyInfo.builder()
				.comEmail(beforeCommitUserList.get(0).getUserEmail())
				.role(CommonUserRole.ROLE_COM.getUserRole())
				.build();

		AppRoomView appRoomView = AppRoomView.builder()
				.roomDesc("roomdesc")
				.maxCount(10)
				.checkIn(LocalDateTime.now())
				.checkOut(LocalDateTime.now())
				.couponYn(CommonConstant.Y.getName())
				.roomNm("roomNm")
				.comName("comName")
				.sellPrc(100000)
				.build();


		UserMS afterCommitUser = userService.signUpUser(beforeCommitUserList.get(0));

		CompanyMS company = companyService.createCompany(beforeCommitCompany);

		//when
		AppRoomDTO appRoomDTO = appRoomService.companyRoomCreate(loginUserInfo, appRoomView, null, null);

		//then
		assertEquals(company.getId(), appRoomDTO.getComId());
		assertEquals(company.getCom_nm(), appRoomDTO.getComName());
		assertEquals(appRoomView.getRoomDesc(), appRoomDTO.getRoomDesc());
	}

	@DisplayName("객실 예약 테스트")
	@Test
	@Transactional
	void RoomReserveTest() throws InterruptedException {

		AppRoomDTO method = createMethod();

		entityManager.flush();
		entityManager.clear();
		AppBookingView appBookingView = new AppBookingView(method.getId(), "N", 1);

		int threadCnt = 100;
		// thread 사용할 수 있는 서비스 선언, 몇 개의 스레드 사용할건지 지정
		ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);
		// 다른 스레드 작업 완료까지 기다리게 해주는 클래스
		// 몇을 카운트할지 지정
		// countDown()을 통해 0까지 세어야 await()하던 thread가 다시 실행됨
		CountDownLatch latch = new CountDownLatch (threadCnt);

		// thread 실행
		// 보통 for문안에서 여러번 같은 코드를 실행시킴
		for (int i = 0; i < threadCnt; i++) {
				executorService.execute(() -> {
					try {
						LoginUserInfo userInfo = LoginUserInfo.builder().userEmail("test@test.com0").build();

						appBookingService.bookingCreate(appBookingView, userInfo);

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}finally {
						latch.countDown();
					}
				});
		}

		latch.await();

		RoomMS foundRoom = roomMSRepository.findById(appBookingView.getRoomId()).orElseThrow(UserNotFoundException::new);
		assertEquals(0, foundRoom.getMaxCount());
	}

	public AppRoomDTO createMethod() {
		//given
		LoginCompanyInfo loginUserInfo = LoginCompanyInfo.builder()
				.comEmail(beforeCommitUserList.get(0).getUserEmail())
				.role(CommonUserRole.ROLE_COM.getUserRole())
				.build();

		AppRoomView appRoomView = AppRoomView.builder()
				.roomDesc("roomdesc")
				.maxCount(100)
				.checkIn(LocalDateTime.now())
				.checkOut(LocalDateTime.now())
				.couponYn(CommonConstant.Y.getName())
				.roomNm("roomNm")
				.comName("comName")
				.sellPrc(100000)
				.build();

		UserMS afterCommitUser = transaction.execute((status -> userService.signUpUser(beforeCommitUserList.get(0))));

		CompanyMS company = transaction.execute((status -> companyService.createCompany(beforeCommitCompany)));

		AppRoomDTO appRoomDTO = transaction.execute((status -> appRoomService.companyRoomCreate(loginUserInfo, appRoomView, null, null)));

		return appRoomDTO;

	}
}
