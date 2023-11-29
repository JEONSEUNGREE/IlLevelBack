package com.trip.penguin.cs;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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
import com.trip.penguin.cs.controller.AppCsController;
import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.service.AppCsService;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.resolver.vo.LoginInfo;

import jakarta.servlet.http.Cookie;

@ActiveProfiles("test")
@WebMvcTest(AppCsController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class AppCsWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppCsService appCsService;

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

		given(appCsService.userMyPageCsqCreate(
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
		mockMvc.perform(post("/cs/create")
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
		given(appCsService.userMyPageCsqRead(
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
		mockMvc.perform(get("/cs/read/{csqId}", 1)
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
		given(appCsService.userMyPageCsqList(
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
		mockMvc.perform(get("/cs/list/{curPage}", 1)
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
		mockMvc.perform(get("/cs/delete/{csqId}", 1)
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
}
