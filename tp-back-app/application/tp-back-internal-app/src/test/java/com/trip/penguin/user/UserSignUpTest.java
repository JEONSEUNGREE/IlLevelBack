package com.trip.penguin.user;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.security.dto.SignUpDTO;
import com.trip.penguin.signup.SignupController;

@ActiveProfiles("test")
@WebMvcTest(SignupController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class UserSignUpTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DefaultUserService defaultUserService;

	@Test
	public void userSignUpTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
			new SignUpDTO(
				"seoul",
				"test@test.com",
				"userPwd",
				"userFirstName",
				"userLastName",
				"userNickName"
			)
		);

		// when
		mockMvc.perform(post("/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				// then
				requestFields(
					fieldWithPath("userCity").type(JsonFieldType.STRING).description("회원 거주지").optional(),
					fieldWithPath("userEmail").type(JsonFieldType.STRING).description("회원 이메일"),
					fieldWithPath("userPwd").type(JsonFieldType.STRING).description("회원 비밀번호"),
					fieldWithPath("userFirst").type(JsonFieldType.STRING).description("회원 이름"),
					fieldWithPath("userLast").type(JsonFieldType.STRING).description("회원 성"),
					fieldWithPath("userNick").type(JsonFieldType.STRING).description("닉네임").optional()
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
				)));

	}
}
