package com.trip.penguin.user;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.follow.service.FollowService;
import com.trip.penguin.user.dto.UserFollowDTO;
import com.trip.penguin.user.dto.UserFollowListDTO;
import com.trip.penguin.user.repository.UserFollowCustomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.exception.DataNotFoundException;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserCsqDetailDTO;
import com.trip.penguin.user.dto.UserCsqPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.user.view.UserCsqView;
import com.trip.penguin.util.ImgUtils;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
		"com.trip.penguin.user",
		"com.trip.penguin.cs",
		"com.trip.penguin.follow"
}, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = UserMyPageController.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class UserMyPageDataTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMyPageService userMyPageService;

	@Autowired
	private EntityManager em;

	@Autowired
	private FollowService followService;

	@MockBean
	private DefaultUserService defaultUserService;

	@MockBean
	private ImgUtils imgUtils;

	private List<UserMS> beforeCommitUserList = new ArrayList<>();

	private List<UserMS> afterCommitUserList = new ArrayList<>();

	@BeforeEach
	public void beforeData() {

		/* 회원 가입 정보 */
		for (int i = 0; i < 10; i++) {
			beforeCommitUserList.add(
					UserMS.builder()
							.offYn("N")
							.userCity("Seoul"+ i)
							.userImg("default"+ i)
							.userEmail("test@test.com"+ i)
							.userRole("user"+ i)
							.userNick("default"+ i)
							.userPwd("test"+ i)
							.userFirst("t"+ i)
							.userLast("est"+ i)
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

		UserCsqDetailDTO afterCommitCsq = userMyPageService.userMyPageCsqCreate(
				loginInfo,
				UserCsqView.builder().csqTitle("TestTitle").csqContent("TestContent").build());

		em.flush();
		em.clear();

		//when
		UserCsqDetailDTO foundCsq = userMyPageService.userMyPageCsqRead(loginInfo, afterCommitCsq.getId().intValue());

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
			userMyPageService.userMyPageCsqCreate(
					loginInfo,
					UserCsqView.builder().csqTitle("TestTitle" + i).csqContent("TestContent" + i).build());
		}

		em.flush();
		em.clear();

		//when
		UserCsqPageDTO userCsqPageDTO = userMyPageService.userMyPageCsqList(loginInfo, 0);

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

		userMyPageService.userMyPageCsqCreate(
				loginInfoRealUser,
				UserCsqView.builder().csqTitle("TestTitle1").csqContent("TestContent1").build());

		userMyPageService.userMyPageCsqCreate(
				loginInfoRealUser,
				UserCsqView.builder().csqTitle("TestTitle2").csqContent("TestContent2").build());

		//when
		UserCsqPageDTO userCsqPageDTO = userMyPageService.userMyPageCsqList(loginInfoRealUser, 0);
		userMyPageService.userMyPageCsqDelete(loginInfoRealUser, userCsqPageDTO.getCsqList().get(0).getId().intValue());

		//then
		assertEquals(beforeCommitUserList.get(0).getUserEmail(), afterCommitUser.getUserEmail());
		// 삭제 확인
		assertThrows(DataNotFoundException.class, () -> userMyPageService.userMyPageCsqDelete(loginInfoRealUser,
				userCsqPageDTO.getCsqList().get(0).getId().intValue()));
		// 타인 삭제 불가 확인
		assertThrows(UserNotAllowedException.class, () -> userMyPageService.userMyPageCsqDelete(loginInfoFakeUser,
				userCsqPageDTO.getCsqList().get(1).getId().intValue()));
	}

	@DisplayName("마이 페이지 팔로우 등록")
	@Test
	void userMyPageFollowAddTest() {

		//given
		LoginInfo loginInfo1 = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		UserMS afterCommitUser1 = userService.signUpUser(beforeCommitUserList.get(0));
		UserMS afterCommitUser2 = userService.signUpUser(beforeCommitUserList.get(1));

		//when
		UserFollowDTO afterAddFollow = userMyPageService.userMyPageFollowAdd(loginInfo1, afterCommitUser2.getId().intValue());

		em.flush();
		em.clear();

		//then
		assertEquals(afterCommitUser2.getUserNick(), afterAddFollow.getUserNick());
		assertEquals(afterCommitUser2.getId(), afterAddFollow.getFollowId());
		assertEquals(afterCommitUser2.getUserImg(), afterAddFollow.getUserImg());
	}

	@DisplayName("마이 페이지 팔로우 목록")
	@Test
	void userMyPageFollowListTest() {

		//given
		LoginInfo loginInfo1 = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		for (int i = 0; i < beforeCommitUserList.size(); i++) {
			afterCommitUserList.add(userService.signUpUser(beforeCommitUserList.get(i)));
		}

		for (int i = 1; i < afterCommitUserList.size(); i++) {
			userMyPageService.userMyPageFollowAdd(loginInfo1, afterCommitUserList.get(i).getId().intValue());
		}

		//when
		UserFollowListDTO userFollowListDTO = userMyPageService.userMyPageFollowList(loginInfo1, 0);

		//then
		assertEquals(userFollowListDTO.getUserFollowList().size(), 5);
		assertEquals(userFollowListDTO.getTotalPage(), 2);
		assertEquals(userFollowListDTO.getFollowCount(), 9);
	}

	@DisplayName("마이 페이지 팔로우 삭제")
	@Test
	void userMyPageFollowDeleteTest() {

		//given
		LoginInfo loginInfo1 = LoginInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		UserMS afterCommitUser1 = userService.signUpUser(beforeCommitUserList.get(0));
		UserMS afterCommitUser2 = userService.signUpUser(beforeCommitUserList.get(1));

		//when
		UserFollowDTO afterAddFollow = userMyPageService.userMyPageFollowAdd(loginInfo1, afterCommitUser2.getId().intValue());
		userMyPageService.userMyPageFollowDelete(loginInfo1, afterAddFollow.getFollowId());

		em.flush();
		em.clear();

		UserFollowListDTO userMyPageFollowList = userMyPageService.userMyPageFollowList(loginInfo1, 0);

		//then
		assertEquals(userMyPageFollowList.getFollowCount(), 0);
	}
}
