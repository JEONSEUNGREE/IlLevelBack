package com.trip.penguin.cs;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.service.AppCsService;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.exception.DataNotFoundException;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
	"com.trip.penguin.user",
	"com.trip.penguin.cs",
}, excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {UserMyPageController.class, UserMyPageService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class AppDataCsTest {

	@Autowired
	private UserService userService;

	@Autowired
	private AppCsService appCsService;

	private List<UserMS> beforeCommitUserList = new ArrayList<>();

	@BeforeEach
	public void beforeData() {

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

	}

	@DisplayName("마이 페이지 고객 센터 문의 생성, 읽기")
	@Test
	void userMyPageCsqCreateReadTest() {

		//given
		LoginInfo loginInfo = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();
		UserMS afterCommitUser = userService.signUpUser(beforeCommitUserList.get(0));

		UserCsqDetailDTO afterCommitCsq = appCsService.userMyPageCsqCreate(
			loginInfo,
			UserCsqView.builder().csqTitle("TestTitle").csqContent("TestContent").build());

		//when
		UserCsqDetailDTO foundCsq = appCsService.userMyPageCsqRead(loginInfo, afterCommitCsq.getId().intValue());

		//then
		assertEquals(beforeCommitUserList.get(0).getUserEmail(), afterCommitUser.getUserEmail());
		assertEquals(afterCommitCsq.getCsqContent(), foundCsq.getCsqContent());
		assertEquals(afterCommitCsq.getCsqTitle(), foundCsq.getCsqTitle());
	}

	@DisplayName("마이 페이지 고객 센터 문의 목록 조회")
	@Test
	void userMyPageCsqReadListTest() {

		//given
		LoginInfo loginInfo = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();
		UserMS afterCommitUser = userService.signUpUser(beforeCommitUserList.get(0));

		for (int i = 1; i <= 3; i++) {
			appCsService.userMyPageCsqCreate(
				loginInfo,
				UserCsqView.builder().csqTitle("TestTitle" + i).csqContent("TestContent" + i).build());
		}

		//when
		UserCsqPageDTO userCsqPageDTO = appCsService.userMyPageCsqList(loginInfo, 0);

		//then
		assertEquals(beforeCommitUserList.get(0).getUserEmail(), afterCommitUser.getUserEmail());
		assertEquals(userCsqPageDTO.getCsqList().get(0).getCsqTitle(), "TestTitle1");
		assertEquals(userCsqPageDTO.getPageNumber(), 0);
		assertEquals(userCsqPageDTO.getTotalPage(), 1);
	}

	@DisplayName("마이 페이지 고객 센터 문의 삭제")
	@Test
	void userMyPageCsqDeleteTest() {

		//given
		LoginInfo loginInfoRealUser = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();
		LoginInfo loginInfoFakeUser = LoginInfo.builder().userEmail("fake@fake.com").build();

		UserMS afterCommitUser = userService.signUpUser(beforeCommitUserList.get(0));

		appCsService.userMyPageCsqCreate(
			loginInfoRealUser,
			UserCsqView.builder().csqTitle("TestTitle1").csqContent("TestContent1").build());

		appCsService.userMyPageCsqCreate(
			loginInfoRealUser,
			UserCsqView.builder().csqTitle("TestTitle2").csqContent("TestContent2").build());

		//when
		UserCsqPageDTO userCsqPageDTO = appCsService.userMyPageCsqList(loginInfoRealUser, 0);
		appCsService.userMyPageCsqDelete(loginInfoRealUser, userCsqPageDTO.getCsqList().get(0).getId().intValue());

		//then
		assertEquals(beforeCommitUserList.get(0).getUserEmail(), afterCommitUser.getUserEmail());
		// 삭제 확인
		assertThrows(DataNotFoundException.class, () -> appCsService.userMyPageCsqDelete(loginInfoRealUser,
			userCsqPageDTO.getCsqList().get(0).getId().intValue()));
		// 타인 삭제 불가 확인
		assertThrows(UserNotAllowedException.class, () -> appCsService.userMyPageCsqDelete(loginInfoFakeUser,
			userCsqPageDTO.getCsqList().get(1).getId().intValue()));
	}
}
