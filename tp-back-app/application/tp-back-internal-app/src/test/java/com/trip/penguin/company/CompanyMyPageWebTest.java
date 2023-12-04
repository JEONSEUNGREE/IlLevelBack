package com.trip.penguin.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.company.controller.AppCompanyController;
import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.service.AppCompanyService;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.config.WithMockCustomUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;
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

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(AppCompanyController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class CompanyMyPageWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppCompanyService appCompanyService;

	@Test
	@DisplayName("기업 회원 정보 수정")
	@WithMockCustomUser
	public void companyModifyTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
			new AppCompanyView(
				"changedAddress"
			)
		);

		MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "image.png"
			, MediaType.MULTIPART_FORM_DATA_VALUE, "example".getBytes());

		MockMultipartFile requestBody = new MockMultipartFile("appCompanyView", "",
			MediaType.APPLICATION_JSON_VALUE, requestJson.getBytes());

		given(appCompanyService.appCompanyModify(
				any(LoginCompanyInfo.class), any(AppCompanyView.class), any(MultipartFile.class)))
				.willReturn(
						AppCompanyDTO.builder()
								.comImg("changedImg")
								.comAddress("changedAddress")
								.build()
				);

		// when
		mockMvc.perform(multipart("/company/modify")
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
					partWithName("multipartFile").description("기업 이미지 파일").optional(),
					partWithName("appCompanyView").description("회원 정보 수정")
				),
				requestPartFields(
					"appCompanyView",
					fieldWithPath("comAddress").type(JsonFieldType.STRING).description("기업 주소 변경")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.comImg").type(JsonFieldType.STRING).description("기업 이미지 변경"),
					fieldWithPath("data.comAddress").type(JsonFieldType.STRING).description("기업 주소 변경")
				))
			);
	}
}
