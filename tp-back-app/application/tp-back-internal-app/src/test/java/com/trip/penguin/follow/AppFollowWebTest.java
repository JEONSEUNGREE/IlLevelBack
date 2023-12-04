package com.trip.penguin.follow;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.config.WithMockCustomUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.follow.controller.AppFollowController;
import com.trip.penguin.follow.service.AppFollowService;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.dto.UserFollowListDTO;

import jakarta.servlet.http.Cookie;

@ActiveProfiles("test")
@WebMvcTest(AppFollowController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class AppFollowWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppFollowService appFollowService;

	@Test
	@DisplayName("팔로우 등록")
	@WithMockCustomUser
	public void userMyPageFollowAddTest() throws Exception {

		// given
		given(appFollowService.userMyPageFollowAdd(
			any(LoginUserInfo.class), any(Integer.class)))
			.willReturn(
				UserFollowDTO.builder()
					.followId(1L)
					.userNick("userNick")
					.userImg("userImg")
					.build()
			);

		// when
		mockMvc.perform(get("/follow/add/{followId}", 1)
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				pathParameters(
					parameterWithName("followId").description("팔로우할 대상 ID")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.followId").type(JsonFieldType.NUMBER).description("팔로우한 대상 ID"),
					fieldWithPath("data.userNick").type(JsonFieldType.STRING).description("팔로우한 대상 닉네임"),
					fieldWithPath("data.userImg").type(JsonFieldType.STRING).description("팔로우한 대상 프로필 이미지")
				))
			);
	}

	@Test
	@DisplayName("팔로우 목록")
	@WithMockCustomUser
	public void userMyPageFollowListTest() throws Exception {

		// given
		given(appFollowService.userMyPageFollowList(
			any(LoginUserInfo.class), any(Integer.class)))
			.willReturn(
				UserFollowListDTO.builder()
					.userFollowList(List.of(
						UserFollowDTO.builder()
							.userImg("userImage")
							.followId(1L)
							.userNick("userNick")
							.build()))
					.followCount(1)
					.totalPage(1)
					.build()
			);

		// when
		mockMvc.perform(get("/follow/list/{curPage}", 1)
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				pathParameters(
					parameterWithName("curPage").description("현재 페이지")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.userFollowList[]").type(JsonFieldType.ARRAY).description("팔로우 대상 목록"),
					fieldWithPath("data.userFollowList[].followId").type(JsonFieldType.NUMBER)
						.description("팔로우한 대상 목록"),
					fieldWithPath("data.userFollowList[].userImg").type(JsonFieldType.STRING)
						.description("팔로우한 대상 프로필 이미지"),
					fieldWithPath("data.userFollowList[].userNick").type(JsonFieldType.STRING)
						.description("팔로우한 대상 닉임네"),
					fieldWithPath("data.followCount").type(JsonFieldType.NUMBER).description("팔로우 수"),
					fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지 수")
				))
			);
	}

	@Test
	@DisplayName("팔로우 삭제")
	@WithMockCustomUser
	public void userMyPageFollowDeleteTest() throws Exception {

		// when
		mockMvc.perform(get("/follow/delete/{followId}", 1)
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				pathParameters(
					parameterWithName("followId").description("팔로우 취소할 대상 아이디")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
				))
			);
	}
}
