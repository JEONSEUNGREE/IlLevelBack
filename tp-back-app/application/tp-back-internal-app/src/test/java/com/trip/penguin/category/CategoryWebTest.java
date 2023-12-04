package com.trip.penguin.category;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.trip.penguin.category.controller.AppCategoryController;
import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.service.AppCategoryService;
import com.trip.penguin.category.view.AppCategoryView;
import com.trip.penguin.config.AbstractRestDocsTests;

@ActiveProfiles("test")
@WebMvcTest(AppCategoryController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class CategoryWebTest extends AbstractRestDocsTests {

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppCategoryService appCategoryService;

	@Test
	@DisplayName("카테고리 등록")
	public void createCategory() throws Exception {

		// given
		String requestJson = objectMapper.writeValueAsString(
			new AppCategoryView(
				"기초 케어",
				0,
				1L
			)
		);

		given(appCategoryService.appCategoryCreate(
			any(AppCategoryView.class))
		)
			.willReturn(
				CategoryDepthDTO.builder()
					.id(1L)
					.depth(0)
					.cateName("기초 케어")
					.parentCateName("기초 케어")
					.ancestor(1L)
					.parentId(1L)
					.childCateList(new ArrayList<>())
					.build()
			);

		// when
		mockMvc.perform(post("/category/create")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				// then
				requestFields(
					fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 이름"),
					fieldWithPath("depth").type(JsonFieldType.NUMBER).description("카테고리 뎁스"),
					fieldWithPath("parentId").type(JsonFieldType.NUMBER).description("상위 카테고리 ID")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("카테고리 아이디"),
					fieldWithPath("data.ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data.parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data.cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data.depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록")
				)));
	}

	@Test
	@DisplayName("카테고리 맵 형태로 조회 key를 뎁스로 value를 카테고리 목록으로 조회")
	public void getUntilCurrentDepthCateKeyMap() throws Exception {

		// given
		given(appCategoryService.getUntilCurrentDepthCateKeyMap(
			any(Long[].class), any(Integer.class))
		)
			.willReturn(
				Map.of(0,
					List.of(CategoryDepthDTO.builder()
						.id(1L)
						.depth(0)
						.cateName("기초 케어")
						.parentCateName("기초 케어")
						.ancestor(1L)
						.parentId(1L)
						.childCateList(new ArrayList<>())
						.build()
					),
					1,
					List.of(CategoryDepthDTO.builder()
						.id(5L)
						.depth(1)
						.cateName("화장품")
						.parentCateName("기초 케어")
						.ancestor(1L)
						.parentId(1L)
						.childCateList(new ArrayList<>())
						.build()
					),
					2,
					List.of(CategoryDepthDTO.builder()
						.id(6L)
						.depth(2)
						.cateName("남성용")
						.parentCateName("화장품")
						.ancestor(1L)
						.parentId(5L)
						.childCateList(new ArrayList<>())
						.build()
					)
				)
			);

		// when
		mockMvc.perform(get("/category/untilDepth/map?ancestors=1,2&depth=2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			//then
			.andDo(restDocs.document(
				queryParameters(parameterWithName("ancestors").description("최상위 카테고리 ID"),
					parameterWithName("depth").description("현재 뎁스까지 조회")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
					fieldWithPath("data.0").type(JsonFieldType.ARRAY).description("카테고리 분류된 0 뎁스"),
					fieldWithPath("data.0.[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data.0.[].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data.0.[].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data.0.[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.0.[].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data.0.[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.0.[].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data.1").type(JsonFieldType.ARRAY).description("카테고리 분류된 1 뎁스"),
					fieldWithPath("data.1.[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data.1.[].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data.1.[].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data.1.[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.1.[].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data.1.[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.1.[].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data.2").type(JsonFieldType.ARRAY).description("카테고리 분류된 2 뎁스"),
					fieldWithPath("data.2.[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data.2.[].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data.2.[].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data.2.[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.2.[].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data.2.[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.2.[].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록")
				)));
	}

	@Test
	@DisplayName("카테고리 뎁스와 지정된 최상위 카테고리를 통해 계층 구조로 조회")
	public void getUntilCurrentDepthCateList() throws Exception {

		// given
		given(appCategoryService.getUntilCurrentDepthCateList(
			any(Long[].class), any(Integer.class))
		)
			.willReturn(
				List.of(CategoryDepthDTO.builder()
					.id(1L)
					.depth(0)
					.cateName("기초 케어")
					.parentCateName("기초 케어")
					.ancestor(1L)
					.parentId(1L)
					.childCateList(
						List.of(CategoryDepthDTO.builder()
							.id(5L)
							.depth(1)
							.cateName("화장품")
							.parentCateName("기초 케어")
							.ancestor(1L)
							.parentId(1L)
							.childCateList(
								List.of(CategoryDepthDTO.builder()
									.id(6L)
									.depth(2)
									.cateName("남성용")
									.parentCateName("화장품")
									.ancestor(1L)
									.parentId(5L)
									.childCateList(new ArrayList<>())
									.build()
								)
							).build())
					).build()
				));

		// when
		mockMvc.perform(get("/category/untilDepth/list?ancestors=1,2&depth=2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			//then
			.andDo(restDocs.document(
				queryParameters(parameterWithName("ancestors").description("최상위 카테고리 ID"),
					parameterWithName("depth").description("현재 뎁스까지 조회")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("카테고리 정보"),
					fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data[].childCateList[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[].childCateList[].ancestor").type(JsonFieldType.NUMBER)
						.description("최상위 카테고리 ID"),
					fieldWithPath("data[].childCateList[].parentId").type(JsonFieldType.NUMBER)
						.description("부모 카테고리 ID"),
					fieldWithPath("data[].childCateList[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[].childCateList[].parentCateName").type(JsonFieldType.STRING)
						.description("부모 카테고리 명"),
					fieldWithPath("data[].childCateList[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[].childCateList[].childCateList[]").type(JsonFieldType.ARRAY)
						.description("하위 카테고리 목록"),
					fieldWithPath("data[].childCateList[].childCateList[].ancestor").type(JsonFieldType.NUMBER)
						.description("최상위 카테고리 ID"),
					fieldWithPath("data[].childCateList[].childCateList[].id").type(JsonFieldType.NUMBER)
						.description("카테고리 ID"),
					fieldWithPath("data[].childCateList[].childCateList[].parentId").type(JsonFieldType.NUMBER)
						.description("부모 카테고리 ID"),
					fieldWithPath("data[].childCateList[].childCateList[].cateName").type(JsonFieldType.STRING)
						.description("카테고리 명"),
					fieldWithPath("data[].childCateList[].childCateList[].parentCateName").type(JsonFieldType.STRING)
						.description("부모 카테고리 명"),
					fieldWithPath("data[].childCateList[].childCateList[].depth").type(JsonFieldType.NUMBER)
						.description("뎁스"),
					fieldWithPath("data[].childCateList[].childCateList[].childCateList").type(JsonFieldType.ARRAY)
						.description("하위 카테고리 목록")
				)));
	}

	@Test
	@DisplayName("현재 카테고리의 하위 카테고리들만 조회")
	public void getCurrentDepthChildCateList() throws Exception {

		// given
		given(appCategoryService.getCurrentDepthChildCateList(
			any(Integer.class), any(Long.class))
		)
			.willReturn(
				List.of(
					CategoryDepthDTO.builder()
						.id(6L)
						.depth(2)
						.cateName("지갑")
						.parentCateName("의류")
						.ancestor(1L)
						.parentId(3L)
						.childCateList(new ArrayList<>())
						.build(),
					CategoryDepthDTO.builder()
						.id(5L)
						.depth(2)
						.cateName("벨트")
						.parentCateName("의류")
						.ancestor(1L)
						.parentId(3L)
						.childCateList(new ArrayList<>())
						.build(),
					CategoryDepthDTO.builder()
						.id(7L)
						.depth(2)
						.cateName("신발")
						.parentCateName("의류")
						.ancestor(1L)
						.parentId(3L)
						.childCateList(new ArrayList<>())
						.build()
				));

		// when
		mockMvc.perform(get("/category/depthList/current/child?id=1&depth=1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			//then
			.andDo(restDocs.document(
				queryParameters(parameterWithName("id").description("현재 카테고리 ID"),
					parameterWithName("depth").description("현재 뎁스까지 조회")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("카테고리 정보"),
					fieldWithPath("data[0].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[0].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[0].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[0].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[0].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[0].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[0].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data[1].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[1].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[1].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[1].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[1].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[1].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[1].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data[2].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[2].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[2].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[2].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[2].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[2].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[2].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록")
				)));
	}

	@Test
	@DisplayName("현재 카테고리에 해당되는 부모 카테고리들만 계층 구조로 조회")
	public void getUntilCurrentDepthFlatCate() throws Exception {

		// given
		given(appCategoryService.getUntilCurrentDepthFlatCate(
			any(Long.class), any(Integer.class), any(Long.class)))
			.willReturn(CategoryDepthDTO.builder()
				.id(1L)
				.depth(0)
				.cateName("기초 케어")
				.parentCateName("기초 케어")
				.ancestor(1L)
				.parentId(1L)
				.childCateList(
					List.of(CategoryDepthDTO.builder()
						.id(5L)
						.depth(1)
						.cateName("화장품")
						.parentCateName("기초 케어")
						.ancestor(1L)
						.parentId(1L)
						.childCateList(
							List.of(CategoryDepthDTO.builder()
								.id(6L)
								.depth(2)
								.cateName("남성용")
								.parentCateName("화장품")
								.ancestor(1L)
								.parentId(5L)
								.childCateList(new ArrayList<>())
								.build()
							)
						).build()
					)
				).build());

		// when
		mockMvc.perform(get("/category/depthList/flat?ancestor=1&depth=2&id=6")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			//then
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("ancestor").description("최상위 카테고리 ID"),
					parameterWithName("depth").description("현재 뎁스까지 조회"),
					parameterWithName("id").description("현재 카테고리 ID")
				),
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data").type(JsonFieldType.OBJECT).description("카테고리 정보"),
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data.ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data.parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data.cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data.depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.childCateList[]").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data.childCateList[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data.childCateList[].ancestor").type(JsonFieldType.NUMBER)
						.description("최상위 카테고리 ID"),
					fieldWithPath("data.childCateList[].parentId").type(JsonFieldType.NUMBER)
						.description("부모 카테고리 ID"),
					fieldWithPath("data.childCateList[].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data.childCateList[].parentCateName").type(JsonFieldType.STRING)
						.description("부모 카테고리 명"),
					fieldWithPath("data.childCateList[].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data.childCateList[].childCateList[]").type(JsonFieldType.ARRAY)
						.description("하위 카테고리 목록"),
					fieldWithPath("data.childCateList[].childCateList[].ancestor").type(JsonFieldType.NUMBER)
						.description("최상위 카테고리 ID"),
					fieldWithPath("data.childCateList[].childCateList[].id").type(JsonFieldType.NUMBER)
						.description("카테고리 ID"),
					fieldWithPath("data.childCateList[].childCateList[].parentId").type(JsonFieldType.NUMBER)
						.description("부모 카테고리 ID"),
					fieldWithPath("data.childCateList[].childCateList[].cateName").type(JsonFieldType.STRING)
						.description("카테고리 명"),
					fieldWithPath("data.childCateList[].childCateList[].parentCateName").type(JsonFieldType.STRING)
						.description("부모 카테고리 명"),
					fieldWithPath("data.childCateList[].childCateList[].depth").type(JsonFieldType.NUMBER)
						.description("뎁스"),
					fieldWithPath("data.childCateList[].childCateList[].childCateList").type(JsonFieldType.ARRAY)
						.description("하위 카테고리 목록")
				)));
	}

	@Test
	@DisplayName("모든 카테고리 상위 -> 하위 계층 구조로 조회")
	public void getAllCategory() throws Exception {

		// given
		given(appCategoryService.getAllCategory())
			.willReturn(List.of(
				CategoryDepthDTO.builder()
					.id(6L)
					.depth(0)
					.cateName("지갑")
					.parentCateName("지갑")
					.ancestor(6L)
					.parentId(6L)
					.childCateList(new ArrayList<>())
					.build(),
				CategoryDepthDTO.builder()
					.id(3L)
					.depth(0)
					.cateName("벨트")
					.parentCateName("벨트")
					.ancestor(3L)
					.parentId(3L)
					.childCateList(new ArrayList<>())
					.build(),
				CategoryDepthDTO.builder()
					.id(4L)
					.depth(0)
					.cateName("신발")
					.parentCateName("신발")
					.ancestor(4L)
					.parentId(4L)
					.childCateList(new ArrayList<>())
					.build()
			));

		// when
		mockMvc.perform(get("/category/getAllCate")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			//then
			.andDo(restDocs.document(
				responseFields(
					fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
					fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
					fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("카테고리 정보"),
					fieldWithPath("data[0].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[0].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[0].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[0].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[0].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[0].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[0].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data[1].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[1].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[1].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[1].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[1].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[1].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[1].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록"),
					fieldWithPath("data[2].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
					fieldWithPath("data[2].ancestor").type(JsonFieldType.NUMBER).description("최상위 카테고리 ID"),
					fieldWithPath("data[2].parentId").type(JsonFieldType.NUMBER).description("부모 카테고리 ID"),
					fieldWithPath("data[2].cateName").type(JsonFieldType.STRING).description("카테고리 명"),
					fieldWithPath("data[2].parentCateName").type(JsonFieldType.STRING).description("부모 카테고리 명"),
					fieldWithPath("data[2].depth").type(JsonFieldType.NUMBER).description("뎁스"),
					fieldWithPath("data[2].childCateList").type(JsonFieldType.ARRAY).description("하위 카테고리 목록")
				)));
	}

}
