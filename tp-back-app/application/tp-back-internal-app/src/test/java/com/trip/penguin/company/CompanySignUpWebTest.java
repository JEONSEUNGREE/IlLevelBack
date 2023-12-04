package com.trip.penguin.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.account.dto.SignUpCompanyDTO;
import com.trip.penguin.account.service.CompanyUserService;
import com.trip.penguin.account.service.DefaultUserService;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.signup.SignupController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(SignupController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class CompanySignUpWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DefaultUserService defaultUserService;

	@MockBean
	private CompanyUserService companyUserService;

	@Test
	@DisplayName("기업 회원 가입")
	public void companySignUpTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
				new SignUpCompanyDTO(
						"toryCompany",
						"test@test.com",
						"comPwd",
						"comAddress"
				)
		);

		// when
		mockMvc.perform(post("/com/signup")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(requestJson))
				.andExpect(status().isOk())
				.andDo(restDocs.document(
						// then
						requestFields(
								fieldWithPath("com_nm").type(JsonFieldType.STRING).description("기업 이름"),
								fieldWithPath("comEmail").type(JsonFieldType.STRING).description("기업 회원 주소"),
								fieldWithPath("comPwd").type(JsonFieldType.STRING).description("기업 회원 이메일"),
								fieldWithPath("comAddress").type(JsonFieldType.STRING).description("기업 회원 비밀 번호")
								),
						responseFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
								fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
								fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
						)));

	}
}
