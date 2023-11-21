package com.trip.penguin.recommand;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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
import com.trip.penguin.recommand.room.controller.RoomRecController;
import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.service.RoomRecService;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;

@ActiveProfiles("dev")
@WebMvcTest(RoomRecController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class RecRoomWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private RoomRecService roomRecService;

	@Test
	public void mainRecRoomList() throws Exception {

		// given
		given(roomRecService.getMainRecRoomListWithPaging(any()))
			.willReturn(List.of(
				RoomRecDAO.builder()
					.roomNm("roomNmTest")
					.comName("comTest")
					.couponYn("Y")
					.ratingAvg(5D)
					.sellPrc(5000)
					.thumbNail("default")
					.build()
			));

		MainRecRoomSchCdt mainRecRoomSchCdt = MainRecRoomSchCdt.builder()
			.pageSize(2)
			.pageNumber(0)
			.mainSubData(new ArrayList<>())
			.mainViewData(new ArrayList<>())
			.build();

		String requestJson = objectMapper.writeValueAsString(mainRecRoomSchCdt);

		mockMvc.perform(post("/rec/room/mainList")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지"),
					fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("가져올 페이지 크기"),
					fieldWithPath("mainSubData").type(JsonFieldType.ARRAY).description("추가로 필요한 데이터").optional(),
					fieldWithPath("mainViewData").type(JsonFieldType.ARRAY).description("화면 정보").optional()
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
					fieldWithPath("data[].roomNm").type(JsonFieldType.STRING).description("객실명"),
					fieldWithPath("data[].comName").type(JsonFieldType.STRING).description("상호명"),
					fieldWithPath("data[].sellPrc").type(JsonFieldType.NUMBER).description("판매가"),
					fieldWithPath("data[].couponYn").type(JsonFieldType.STRING).description("쿠폰 사용 가능 여부"),
					fieldWithPath("data[].thumbNail").type(JsonFieldType.STRING).description("썸네일"),
					fieldWithPath("data[].ratingAvg").type(JsonFieldType.NUMBER).description("평점")
				)));

	}
}
