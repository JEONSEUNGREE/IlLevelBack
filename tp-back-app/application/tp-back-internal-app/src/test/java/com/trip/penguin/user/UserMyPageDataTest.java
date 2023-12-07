package com.trip.penguin.user;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.account.converter.ProviderUserConverter;
import com.trip.penguin.account.service.OauthUserService;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
	"com.trip.penguin.user",
	"com.trip.penguin.follow",
	"com.trip.penguin.account.service",
	"com.trip.penguin.security.encoder"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class UserMyPageDataTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMyPageService userMyPageService;

	@MockBean
	private ProviderUserConverter providerUserConverter;

	@MockBean
	private OauthUserService oauthUserService;

	@MockBean
	private ImgUtils imgUtils;

	@MockBean
	private CompanyService companyService;

	@Autowired
	private AppFollowService appFollowService;

	private UserMS beforeCommitUser;

	private UserMS beforeCommitUser2;

	@BeforeEach
	public void beforeData() {

		/* 회원 가입 정보 */
		beforeCommitUser = UserMS.builder()
			.offYn("N")
			.userCity("Seoul")
			.userImg("default")
			.userEmail("test@test.com1")
			.userRole("user")
			.userNick("default")
			.userPwd("test")
			.userFirst("t")
			.userLast("est")
			.createdDate(LocalDateTime.now())
			.modifiedDate(LocalDateTime.now())
			.build();

		beforeCommitUser2 = UserMS.builder()
			.offYn("N")
			.userCity("Seoul")
			.userImg("default")
			.userEmail("test@test.com2")
			.userRole("user")
			.userNick("default")
			.userPwd("test")
			.userFirst("t")
			.userLast("est")
			.createdDate(LocalDateTime.now())
			.modifiedDate(LocalDateTime.now())
			.build();
	}

	@DisplayName("기본 회원 가입 테스트")
	@Test
	void userSignupDataTest() {

		//given
		//when
		UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);

		//then
		assertEquals(beforeCommitUser.getUserNick(), afterCommitUser.getUserNick());
		assertEquals(beforeCommitUser.getUserEmail(), afterCommitUser.getUserEmail());
	}

	@DisplayName("회원 정보 수정 테스트")
	@Test
	void userModifyDataTest() throws IllegalAccessException, IOException {

		//given
		UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);

		//when
		UserMyPageDTO afterUpdateUser = userMyPageService.userMyPageModify(
			LoginUserInfo.builder().userEmail("test@test.com1").jwtToken("testToken").build(),
			new UserMyPageView("userTest", "userTest", "userTest", "userTest"), null);

		//then
		assertNotEquals(afterUpdateUser.getUserNick(), afterCommitUser.getUserNick());
		assertNotEquals(afterUpdateUser.getUserCity(), afterCommitUser.getUserCity());
	}

	@DisplayName("회원 마이 프로필 정보 로드")
	@Test
	void getUserMyPageProfile() {

		//given
		UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);
		UserMS afterCommitUser2 = userService.signUpUser(beforeCommitUser2);

		LoginUserInfo loginInfo = LoginUserInfo.builder().userEmail(afterCommitUser.getUserEmail()).build();

		//when
		appFollowService.userMyPageFollowAdd(loginInfo, afterCommitUser2.getId().intValue());

		Integer userFollowCnt1 = appFollowService.getUserFollowCnt(afterCommitUser.getId());
		Integer userFollowerCnt1 = appFollowService.getUserFollowerCnt(afterCommitUser.getId());
		Integer userFollowerCnt2 = appFollowService.getUserFollowerCnt(afterCommitUser2.getId());

		//then
		assertEquals(1, userFollowCnt1);
		assertEquals(0, userFollowerCnt1);
		assertEquals(1, userFollowerCnt2);

	}
}
