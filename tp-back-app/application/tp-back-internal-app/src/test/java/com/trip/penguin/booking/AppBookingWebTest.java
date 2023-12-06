package com.trip.penguin.booking;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

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
import com.trip.penguin.booking.controller.AppBookingController;
import com.trip.penguin.booking.dto.AppBookingDTO;
import com.trip.penguin.booking.service.AppBookingService;
import com.trip.penguin.booking.view.AppBookingView;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.config.WithMockCustomUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.vo.LoginUserInfo;

import jakarta.servlet.http.Cookie;

@ActiveProfiles("test")
@WebMvcTest(AppBookingController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class AppBookingWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppBookingService appBookingService;

	@Test
	@DisplayName("객실 예약 하기")
	@WithMockCustomUser
	public void bookingTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
			new AppBookingView(
				1L,
				CommonConstant.N.getName(),
				2
			)
		);

		given(appBookingService.bookingCreate(
			any(AppBookingView.class), any(LoginUserInfo.class)))
			.willReturn(
				AppBookingDTO.builder()
					.id(1L)
					.bookNm("객실 명")
					.roomId(1L)
					.count(2)
					.sellPrc(1000)
					.payAmount(2000)
					.payMethod("Card")
					.couponYn(CommonConstant.N.getName())
					.checkIn(LocalDateTime.now())
					.checkOut(LocalDateTime.now())
					.createdDate(LocalDateTime.now())
					.modifiedDate(LocalDateTime.now())
					.build()
			);

		// when
		mockMvc.perform(post("/booking/create")
				.cookie(new Cookie(CommonConstant.ACCOUNT_TOKEN.getName(), "jwtToken"))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestJson))
			// then
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestCookies(
					cookieWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT 토큰")
				),
				requestFields(
					fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("객실 ID"),
					fieldWithPath("couponYn").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
					fieldWithPath("count").type(JsonFieldType.NUMBER).description("구매 수량")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("예약 ID"),
					fieldWithPath("data.roomId").type(JsonFieldType.NUMBER).description("객실 ID"),
					fieldWithPath("data.bookNm").type(JsonFieldType.STRING).description("객실 명"),
					fieldWithPath("data.payMethod").type(JsonFieldType.STRING).description("구매 방법"),
					fieldWithPath("data.payAmount").type(JsonFieldType.NUMBER).description("구매 수량"),
					fieldWithPath("data.sellPrc").type(JsonFieldType.NUMBER).description("객실 기본 가격"),
					fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("구매 수량"),
					fieldWithPath("data.couponYn").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
					fieldWithPath("data.checkIn").type(JsonFieldType.STRING).description("체크인 일시"),
					fieldWithPath("data.checkOut").type(JsonFieldType.STRING).description("체크 아웃 일시"),
					fieldWithPath("data.modifiedDate").type(JsonFieldType.STRING).description("객실 생성 일시"),
					fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("객실 수정 일시")
				))
			);
	}
}
