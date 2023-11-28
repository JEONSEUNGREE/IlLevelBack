package com.trip.penguin.user;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trip.penguin.user.dto.*;
import com.trip.penguin.user.view.UserCsqView;
import jakarta.servlet.http.Cookie;
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
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@WebMvcTest(UserMyPageController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class UserMyPageTest extends AbstractRestDocsTests {

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
			any(LoginInfo.class), any(UserMyPageView.class), any(MultipartFile.class)))
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
	@DisplayName("문의 사항 생성")
	@WithMockCustomUser
	public void userMyPageCsqCreateTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
				new UserCsqView(
						"csqTitle",
						"csqContents"
				)
		);

		given(userMyPageService.userMyPageCsqCreate(
				any(LoginInfo.class), any(UserCsqView.class)))
				.willReturn(
						UserCsqDetailDTO.builder()
								.id(1L)
								.csqTitle("csqTitle")
								.csqContent("csqContents")
								.createdDate(LocalDateTime.now())
								.modifiedDate(LocalDateTime.now())
								.build()
				);

		// when
		mockMvc.perform(post("/usr/mypage/cs/create")
						.content(requestJson)
						.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				// then
				.andExpect(status().isOk())
				.andDo(restDocs.document(
						requestCookies(
								cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
						),
						requestFields(
								fieldWithPath("csqTitle").type(JsonFieldType.STRING).description("문의 사항 제목"),
								fieldWithPath("csqContent").type(JsonFieldType.STRING).description("문의 사항 내용")
						),
						responseFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
								fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
								fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
								fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("문의 사항 고유 번호"),
								fieldWithPath("data.csqTitle").type(JsonFieldType.STRING).description("문의 사항 ID"),
								fieldWithPath("data.csqContent").type(JsonFieldType.STRING).description("문의 사항 내용"),
								fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("작성 일자"),
								fieldWithPath("data.modifiedDate").type(JsonFieldType.STRING).description("수정 일자")
						))
				);

	}
	@Test
	@DisplayName("문의 사항 상세 읽기")
	@WithMockCustomUser
	public void userMyPageCsqReadTest() throws Exception {

		// given
		given(userMyPageService.userMyPageCsqRead(
				any(LoginInfo.class), any(Integer.class)))
				.willReturn(
						UserCsqDetailDTO.builder()
								.id(1L)
								.csqTitle("csqTitle")
								.csqContent("csqContents")
								.createdDate(LocalDateTime.now())
								.modifiedDate(LocalDateTime.now())
								.build()
				);

		// when
		mockMvc.perform(get("/usr/mypage/cs/read/{csqId}", 1)
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
								parameterWithName("csqId").description("문의 사항 ID")
						),
						responseFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
								fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
								fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
								fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("문의 사항 고유 번호"),
								fieldWithPath("data.csqTitle").type(JsonFieldType.STRING).description("문의 사항 제목"),
								fieldWithPath("data.csqContent").type(JsonFieldType.STRING).description("문의 사항 내용"),
								fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("작성 일자"),
								fieldWithPath("data.modifiedDate").type(JsonFieldType.STRING).description("수정 일자")
						))
				);
	}

	@Test
	@DisplayName("문의 사항 목록")
	@WithMockCustomUser
	public void userMyPageCsqListTest() throws Exception {

		// given
		given(userMyPageService.userMyPageCsqList(
				any(LoginInfo.class), any(Integer.class)))
				.willReturn(
						UserCsqPageDTO.builder()
								.csqList(List.of(UserCsqDetailDTO.builder()
										.id(1L)
										.csqTitle("csqTitle")
										.csqContent("csqContents")
										.createdDate(LocalDateTime.now())
										.modifiedDate(LocalDateTime.now())
										.build()))
								.pageNumber(1)
								.totalPage(2)
								.build()
				);

		// when
		mockMvc.perform(get("/usr/mypage/cs/list/{curPage}", 1)
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
								fieldWithPath("data.csqList").type(JsonFieldType.ARRAY).description("문의 사항 고유 번호"),
								fieldWithPath("data.csqList[].id").type(JsonFieldType.NUMBER).description("문의 사항 고유 번호"),
								fieldWithPath("data.csqList[].csqTitle").type(JsonFieldType.STRING).description("문의 사항 제목"),
								fieldWithPath("data.csqList[].csqContent").type(JsonFieldType.STRING).description("문의 사항 내용"),
								fieldWithPath("data.csqList[].createdDate").type(JsonFieldType.STRING).description("작성 일자"),
								fieldWithPath("data.csqList[].modifiedDate").type(JsonFieldType.STRING).description("수정 일자"),
								fieldWithPath("data.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지"),
								fieldWithPath("data.totalPage").type(JsonFieldType.NUMBER).description("전체 페이지")
						))
				);
	}

	@Test
	@DisplayName("문의 사항 삭제")
	@WithMockCustomUser
	public void userMyPageCsqDeleteTest() throws Exception {

		// when
		mockMvc.perform(get("/usr/mypage/cs/delete/{csqId}", 1)
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
								parameterWithName("csqId").description("문의 사항 ID")
						),
						responseFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
								fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
								fieldWithPath("data").type(JsonFieldType.NULL).description("데이터").optional()
						))
				);
	}

	@Test
	@DisplayName("팔로우 등록")
	@WithMockCustomUser
	public void userMyPageFollowAddTest() throws Exception {

		// given
		given(userMyPageService.userMyPageFollowAdd(
				any(LoginInfo.class), any(Integer.class)))
				.willReturn(
						UserFollowDTO.builder()
								.followId(1L)
								.userNick("userNick")
								.userImg("userImg")
								.build()
				);

		// when
		mockMvc.perform(get("/usr/mypage/follow/add/{followId}", 1)
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
		given(userMyPageService.userMyPageFollowList(
				any(LoginInfo.class), any(Integer.class)))
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
		mockMvc.perform(get("/usr/mypage/follow/list/{curPage}", 1)
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
								fieldWithPath("data.userFollowList[].followId").type(JsonFieldType.NUMBER).description("팔로우한 대상 목록"),
								fieldWithPath("data.userFollowList[].userImg").type(JsonFieldType.STRING).description("팔로우한 대상 프로필 이미지"),
								fieldWithPath("data.userFollowList[].userNick").type(JsonFieldType.STRING).description("팔로우한 대상 닉임네"),
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
		mockMvc.perform(get("/usr/mypage/follow/delete/{followId}", 1)
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
