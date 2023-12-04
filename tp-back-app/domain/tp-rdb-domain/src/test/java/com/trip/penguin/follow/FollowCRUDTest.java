package com.trip.penguin.follow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trip.penguin.config.JpaDataConfig;
import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.follow.service.FollowService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import jakarta.persistence.EntityManager;

@JpaDataConfig
@DataJpaTest(properties = "classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FollowCRUDTest {

	private final UserService userService;

	private final FollowService followService;

	private final EntityManager em;

	private List<UserMS> userList;

	@Autowired
	public FollowCRUDTest(UserService userService, FollowService followService, EntityManager em) {
		this.followService = followService;
		this.userService = userService;
		this.em = em;
	}

	@BeforeEach
	public void beforeTest() {
		/* 회원 가입 3명 정보 */
		userList = new ArrayList<>();

		for (int i = 1; i <= 3; i++) {
			userList.add(
				UserMS.builder()
					.offYn("N")
					.userCity("Seoul")
					.userImg("default")
					.userEmail("test" + i + "@email.com")
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

	@AfterEach
	public void afterTest() {
		userList = null;
	}

//	@DisplayName("회원 팔로우 목록 테스트")
//	@Test
//	void userFollowListTest() {
//		// given
//
//		/* 회원 가입 2명 */
//		UserMS signUpUser1 = userService.signUpUser(userList.get(0));
//		UserMS signUpUser2 = userService.signUpUser(userList.get(1));
//		UserMS signUpUser3 = userService.signUpUser(userList.get(2));
//
//		em.flush();
//		em.clear();
//
//		/* 1번 회원에 2번 회원을 팔로워 등록 */
//		FollowMS followInfo1 = FollowMS.builder()
//			.userMS(signUpUser1)
//			.followerId(signUpUser2.getId())
//			.build();
//
//		FollowMS followInfo2 = FollowMS.builder()
//			.userMS(signUpUser1)
//			.followerId(signUpUser3.getId())
//			.build();
//
//		followService.setFollow(followInfo1);
//		followService.setFollow(followInfo2);
//
//		em.flush();
//		em.clear();
//
//		// when
//		/* 팔로워 조회 */
//		UserMS resultUser = userService.getUser(signUpUser1).orElseThrow(IllegalArgumentException::new);
//		List<FollowMS> followList = resultUser.getFollowList();
//
//		// then
//		/* 팔로워 검증 */
//		Assertions.assertEquals(signUpUser1.getId(), resultUser.getId());
//		Assertions.assertEquals(signUpUser2.getId(), followList.get(0).getFollowerId());
//		Assertions.assertEquals(signUpUser3.getId(), followList.get(1).getFollowerId());
//		Assertions.assertEquals(2, followList.size());
//
//	}
}
