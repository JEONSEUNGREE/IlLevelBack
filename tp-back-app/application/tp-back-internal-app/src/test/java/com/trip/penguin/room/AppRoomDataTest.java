package com.trip.penguin.room;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonUserRole;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;
import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.service.AppRoomService;
import com.trip.penguin.room.view.AppRoomView;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.service.UserService;
import com.trip.penguin.util.ImgUtils;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
		"com.trip.penguin.user",
		"com.trip.penguin.room",
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
	private CompanyService companyService;

	@Autowired
	private ImgUtils imgUtils;

	private List<UserMS> beforeCommitUserList = new ArrayList<>();

	private CompanyMS beforeCommitCompany;

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
				.maxCount(1)
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
}
