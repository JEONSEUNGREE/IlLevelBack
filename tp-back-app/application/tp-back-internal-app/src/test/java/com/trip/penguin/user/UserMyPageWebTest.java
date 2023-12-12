package com.trip.penguin.user;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.config.WithMockCustomUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.constant.CommonMessage;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageProfileDTO;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

import jakarta.servlet.http.Cookie;

@ActiveProfiles("test")
@WebMvcTest(UserMyPageController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class UserMyPageWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserMyPageService userMyPageService;

	@MockBean
	private ImgUtils imgUtils;

	@Test
	@DisplayName("회원 정보 수정")
	@WithMockCustomUser
	public void userModifyTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
			new UserMyPageView(
				"requestLast",
				"requestFirst",
				"requestNick",
				"requestCity"
			)
		);

		MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "image.png"
			, MediaType.MULTIPART_FORM_DATA_VALUE, "example".getBytes());

		MockMultipartFile requestBody = new MockMultipartFile("userMyPageView", "",
			MediaType.APPLICATION_JSON_VALUE, requestJson.getBytes());

		given(userMyPageService.userMyPageModify(
			any(LoginUserInfo.class), any(UserMyPageView.class), any(MultipartFile.class)))
			.willReturn(
				UserMyPageDTO.builder()
					.userLast("modifiedLast")
					.userFirst("modifiedFirst")
					.userNick("modifiedUserNick")
					.userImg("modifiedUserImg")
					.userCity("modifiedUserCity")
					.build()
			);

		// when
		mockMvc.perform(multipart("/usr/mypage/modify")
				.file(multipartFile)
				.file(requestBody)
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				requestParts(
					partWithName("multipartFile").description("프로필 이미지 파일").optional(),
					partWithName("userMyPageView").description("회원 정보 수정")
				),
				requestPartFields(
					"userMyPageView",
					fieldWithPath("userLast").type(JsonFieldType.STRING).description("회원 성"),
					fieldWithPath("userFirst").type(JsonFieldType.STRING).description("회원 이름"),
					fieldWithPath("userNick").type(JsonFieldType.STRING).description("닉네임"),
					fieldWithPath("userCity").type(JsonFieldType.STRING).description("회원 거주지")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.userLast").type(JsonFieldType.STRING).description("회원 성"),
					fieldWithPath("data.userFirst").type(JsonFieldType.STRING).description("회원 이름"),
					fieldWithPath("data.userNick").type(JsonFieldType.STRING).description("닉네임"),
					fieldWithPath("data.userImg").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
					fieldWithPath("data.userCity").type(JsonFieldType.STRING).description("회원 거주지")
				))
			);
	}

	@Test
	@DisplayName("회원 프로필 정보")
	@WithMockCustomUser
	public void getUserMyPageProfile() throws Exception {

		// given
		given(userMyPageService.getUserMyPageProfile(
			any(LoginUserInfo.class)))
			.willReturn(
				UserMyPageProfileDTO.builder()
					.userEmail("userEmail")
					.userNick("userNick")
					.userImg("userImg")
					.city("city")
					.socialProvider("socialProvider")
					.createdDate(LocalDateTime.now())
					.userFirst("firstName")
					.userLast("lastName")
					.followCnt(1)
					.followerCnt(2)
					.introduce("userIntroduce")
					.build()
			);

		// when
		mockMvc.perform(get("/usr/mypage/profile")
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.userEmail").type(JsonFieldType.STRING).description("회원 이메일"),
					fieldWithPath("data.userNick").type(JsonFieldType.STRING).description("회원 닉네임"),
					fieldWithPath("data.userImg").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
					fieldWithPath("data.city").type(JsonFieldType.STRING).description("회원 거주지"),
					fieldWithPath("data.socialProvider").type(JsonFieldType.STRING).description("회원 가입 유형"),
					fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("회원 가입 일시"),
					fieldWithPath("data.userFirst").type(JsonFieldType.STRING).description("회원 이름"),
					fieldWithPath("data.userLast").type(JsonFieldType.STRING).description("회원 성"),
					fieldWithPath("data.followCnt").type(JsonFieldType.NUMBER).description("회원 팔로우 수"),
					fieldWithPath("data.followerCnt").type(JsonFieldType.NUMBER).description("회원 팔로워 수"),
					fieldWithPath("data.introduce").type(JsonFieldType.STRING).description("회원 소개")
				))
			);
	}

	@Test
	@DisplayName("회원 이메일 체크")
	public void userEmailValid() throws Exception {

		// given
		given(userMyPageService.checkEmailValidate(
			any(String.class)))
			.willReturn(
				CommonMessage.DUPLICATE_EMAIL.getMessage()
			);

		// when
		mockMvc.perform(get("/usr/mypage/valid/email")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.param("email", "test@test.com"))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("email").description("이메일")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING)
						.description("이미 가입된 이메일 형식 입니다,"
							+ "가입 가능한 이메일 형식 입니다, 올바른 이메일 형식이 아닙니다"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
				))
			);
	}
}
