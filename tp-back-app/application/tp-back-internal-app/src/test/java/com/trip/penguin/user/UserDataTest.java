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
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.cs.service.CsMsService;
import com.trip.penguin.oauth.converter.ProviderUserConverter;
import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.oauth.service.OauthUserService;
import com.trip.penguin.recommand.room.repository.RoomRecCustomRepository;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.room.service.RoomService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

import jakarta.persistence.EntityManager;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
	"com.trip.penguin.room",
	"com.trip.penguin.user",
	"com.trip.penguin.recommand.room.repository",
	"com.trip.penguin.recommand.room.service",
	"com.trip.penguin.oauth.service",
	"com.trip.penguin.security.encoder"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class UserDataTest {

	@Autowired
	private RoomRecCustomRepository roomRecService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserMyPageService userMyPageService;

	@Autowired
	private EntityManager em;

	@Autowired
	private DefaultUserService defaultUserService;

	@MockBean
	private ProviderUserConverter providerUserConverter;

	@MockBean
	private OauthUserService oauthUserService;

	@MockBean
	private ImgUtils imgUtils;

	@MockBean
	private CsMsService csMsService;

	private UserMS beforeCommitUser;

	@BeforeEach
	public void beforeData() {

		/* 회원 가입 정보 */
		beforeCommitUser = UserMS.builder()
			.offYn("N")
			.userCity("Seoul")
			.userImg("default")
			.userEmail("test@test.com")
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
			LoginInfo.builder().userEmail("test@test.com").jwtToken("testToken").build(),
			new UserMyPageView("userTest", "userTest", "userTest", "userTest"), null);

		//then
		assertNotEquals(afterUpdateUser.getUserNick(), afterCommitUser.getUserNick());
		assertNotEquals(afterUpdateUser.getUserCity(), afterCommitUser.getUserCity());
	}
}
