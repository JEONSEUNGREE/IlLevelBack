package com.trip.penguin.recommand;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.recommand.room.controller.RoomRecController;
import com.trip.penguin.recommand.room.repository.RoomRecCustomRepository;
import com.trip.penguin.recommand.room.service.RoomRecServiceImpl;

@ActiveProfiles("local")
@WebMvcTest(RoomRecController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class RecRoomWebTest extends AbstractRestDocsTests {

	@MockBean
	private RoomRecCustomRepository roomCustomRepository;

	@MockBean
	private RoomRecServiceImpl roomRecService;

	@Test
	public void sample() throws Exception {
		mockMvc.perform(get("/rec/room/main"))
			.andExpect(status().isOk())
			.andDo(document("sample"));
	}
}
