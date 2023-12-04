package com.trip.penguin.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.company.dto.AppCompanyDTO;
import com.trip.penguin.company.service.AppCompanyService;
import com.trip.penguin.company.view.AppCompanyView;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.config.WithMockCustomUser;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.vo.LoginCompanyInfo;
import com.trip.penguin.room.controller.AppRoomController;
import com.trip.penguin.room.dto.AppRoomDTO;
import com.trip.penguin.room.dto.AppRoomPicDTO;
import com.trip.penguin.room.service.AppRoomService;
import com.trip.penguin.room.view.AppRoomView;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@WebMvcTest(AppRoomController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class RoomWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppRoomService appRoomService;

	@Test
	@DisplayName("객실 정보 수정")
	@WithMockCustomUser
	public void roomModifyTest() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
				new AppRoomView(
						"roomNm",
						"comName",
						1,
						LocalDateTime.now(),
						LocalDateTime.now(),
						"roomDesc",
						"Y",
						5
				)
		);

		List<MockMultipartFile> roomPicList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			roomPicList.add(new MockMultipartFile("roomImgList", i + "image.png"
					, MediaType.MULTIPART_FORM_DATA_VALUE, "example".getBytes()));
		}

		MockMultipartFile thumbNailImg = new MockMultipartFile("thumbNailImg", "image.png", MediaType.MULTIPART_FORM_DATA_VALUE, "example".getBytes());

		MockMultipartFile requestBody = new MockMultipartFile("appRoomView", "",
			MediaType.APPLICATION_JSON_VALUE, requestJson.getBytes());

		given(appRoomService.companyRoomCreate(
				any(LoginCompanyInfo.class), any(AppRoomView.class), any(MultipartFile.class), any(List.class)))
				.willReturn(
						AppRoomDTO.builder()
								.id(1L)
								.comId(1L)
								.comName("toryCompany")
								.soldOutYn("N")
								.couponYn("Y")
								.thumbNail("defaultThumbNail")
								.roomDesc("roomDesc")
								.maxCount(1)
								.sellPrc(100000)
								.roomPicDTOList(
										List.of(AppRoomPicDTO.builder()
												.id(1L)
												.picLocation("UUID + pictureImgName")
												.picSeq(0)
												.modifiedDate(LocalDateTime.now())
												.createdDate(LocalDateTime.now())
												.build()))
								.modifiedDate(LocalDateTime.now())
								.createdDate(LocalDateTime.now())
								.build()
				);

		// when
		mockMvc.perform(multipart("/room/create")
						.file(roomPicList.get(0))
						.file(roomPicList.get(1))
						.file(thumbNailImg)
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
								partWithName("thumbNailImg").description("객실 썸네일 파일").optional(),
								partWithName("roomImgList").description("객실 이미지 파일들").optional(),
								partWithName("appRoomView").description("객실 정보 수정")
						),
						requestPartFields(
								"appRoomView",
								fieldWithPath("roomNm").type(JsonFieldType.STRING).description("객실 이름"),
								fieldWithPath("comName").type(JsonFieldType.STRING).description("회사 이름"),
								fieldWithPath("sellPrc").type(JsonFieldType.NUMBER).description("객실 가격"),
								fieldWithPath("checkIn").type(JsonFieldType.STRING).description("체크 인 시간"),
								fieldWithPath("checkOut").type(JsonFieldType.STRING).description("체크 아웃 시간"),
								fieldWithPath("roomDesc").type(JsonFieldType.STRING).description("객실 설명"),
								fieldWithPath("couponYn").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
								fieldWithPath("maxCount").type(JsonFieldType.NUMBER).description("총 객실 수")
						),
						responseFields(
								fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
								fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
								fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
								fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("객실 ID"),
								fieldWithPath("data.comId").type(JsonFieldType.NUMBER).description("기업 ID"),
								fieldWithPath("data.comName").type(JsonFieldType.STRING).description("기업 명"),
								fieldWithPath("data.soldOutYn").type(JsonFieldType.STRING).description("품절 여부"),
								fieldWithPath("data.thumbNail").type(JsonFieldType.STRING).description("객실 썸네일 이미지"),
								fieldWithPath("data.roomDesc").type(JsonFieldType.STRING).description("객실 설명"),
								fieldWithPath("data.maxCount").type(JsonFieldType.NUMBER).description("총 객실 수"),
								fieldWithPath("data.roomDesc").type(JsonFieldType.STRING).description("객실 설명"),
								fieldWithPath("data.sellPrc").type(JsonFieldType.NUMBER).description("객실 가격"),
								fieldWithPath("data.couponYn").type(JsonFieldType.STRING).description("쿠폰 사용 여부"),
								fieldWithPath("data.roomPicDTOList").type(JsonFieldType.ARRAY).description("객실 사진 정보"),
								fieldWithPath("data.roomPicDTOList[].id").type(JsonFieldType.NUMBER).description("객실 사진 ID"),
								fieldWithPath("data.roomPicDTOList[].picLocation").type(JsonFieldType.STRING).description("객실 사진 위치 명"),
								fieldWithPath("data.roomPicDTOList[].picSeq").type(JsonFieldType.NUMBER).description("객실 사진 순서"),
								fieldWithPath("data.roomPicDTOList[].modifiedDate").type(JsonFieldType.STRING).description("객실 사진 생성 일시"),
								fieldWithPath("data.roomPicDTOList[].createdDate").type(JsonFieldType.STRING).description("객실 사진 수정 일시"),
								fieldWithPath("data.modifiedDate").type(JsonFieldType.STRING).description("객실 생성 일시"),
								fieldWithPath("data.createdDate").type(JsonFieldType.STRING).description("객실 수정 일시")
						))
				);
	}
}
