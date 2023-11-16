package com.trip.penguin.recommand;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.controller.TestController;

@WebMvcTest(TestController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class RecommandRoom extends AbstractRestDocsTests {

	@Test
	public void sample() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(document("sample"));
	}
}
