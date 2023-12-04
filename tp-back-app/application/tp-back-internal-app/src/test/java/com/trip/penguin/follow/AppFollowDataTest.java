package com.trip.penguin.follow;

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
import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
	"com.trip.penguin.user",
	"com.trip.penguin.follow",
}, excludeFilters = @ComponentScan.Filter(
	type = FilterType.ASSIGNABLE_TYPE,
	classes = {UserMyPageController.class, UserMyPageService.class}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class AppFollowDataTest {

	@Autowired
	private UserService userService;

	@Autowired
	private AppFollowService appFollowService;

	private List<UserMS> beforeCommitUserList = new ArrayList<>();

	private List<UserMS> afterCommitUserList = new ArrayList<>();

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

	@DisplayName("마이 페이지 팔로우 등록")
	@Test
	void userMyPageFollowAddTest() {

		//given
		LoginUserInfo loginUserInfo1 = LoginUserInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		UserMS afterCommitUser1 = userService.signUpUser(beforeCommitUserList.get(0));
		UserMS afterCommitUser2 = userService.signUpUser(beforeCommitUserList.get(1));

		//when
		UserFollowDTO afterAddFollow = appFollowService.userMyPageFollowAdd(loginUserInfo1,
			afterCommitUser2.getId().intValue());

		//then
		assertEquals(afterCommitUser2.getUserNick(), afterAddFollow.getUserNick());
		assertEquals(afterCommitUser2.getId(), afterAddFollow.getFollowId());
		assertEquals(afterCommitUser2.getUserImg(), afterAddFollow.getUserImg());
	}

	@DisplayName("마이 페이지 팔로우 목록")
	@Test
	void userMyPageFollowListTest() {

		//given
		LoginUserInfo loginUserInfo1 = LoginUserInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		for (int i = 0; i < beforeCommitUserList.size(); i++) {
			afterCommitUserList.add(userService.signUpUser(beforeCommitUserList.get(i)));
		}

		for (int i = 1; i < afterCommitUserList.size(); i++) {
			appFollowService.userMyPageFollowAdd(loginUserInfo1, afterCommitUserList.get(i).getId().intValue());
		}

		//when
		UserFollowListDTO userFollowListDTO = appFollowService.userMyPageFollowList(loginUserInfo1, 0);

		//then
		assertEquals(userFollowListDTO.getUserFollowList().size(), 5);
		assertEquals(userFollowListDTO.getTotalPage(), 2);
		assertEquals(userFollowListDTO.getFollowCount(), 9);
	}

	@DisplayName("마이 페이지 팔로우 삭제")
	@Test
	void userMyPageFollowDeleteTest() {

		//given
		LoginUserInfo loginUserInfo1 = LoginUserInfo.builder().userEmail(beforeCommitUserList.get(0).getUserEmail()).build();

		UserMS afterCommitUser1 = userService.signUpUser(beforeCommitUserList.get(0));
		UserMS afterCommitUser2 = userService.signUpUser(beforeCommitUserList.get(1));

		//when
		UserFollowDTO afterAddFollow = appFollowService.userMyPageFollowAdd(loginUserInfo1,
			afterCommitUser2.getId().intValue());
		appFollowService.userMyPageFollowDelete(loginUserInfo1, afterAddFollow.getFollowId());

		UserFollowListDTO userMyPageFollowList = appFollowService.userMyPageFollowList(loginUserInfo1, 0);

		//then
		assertEquals(userMyPageFollowList.getFollowCount(), 0);
	}
}
