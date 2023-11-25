package com.trip.penguin.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.AbstractRestDocsTests;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.interceptor.LoginCheckInterceptor;
import com.trip.penguin.resolver.CurrentUserArgResolver;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.controller.UserMyPageController;
import com.trip.penguin.user.dto.UserMyPageDto;
import com.trip.penguin.user.service.UserMyPageService;
import com.trip.penguin.user.view.UserMyPageView;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UserMyPageController.class)
@ContextConfiguration(classes = TpBackInternalApp.class)
public class UserMyPage extends AbstractRestDocsTests {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserMyPageService userMyPageService;

    @Spy
    private LoginCheckInterceptor loginCheckInterceptor;

    @Spy
    private CurrentUserArgResolver argResolver;

    @Test
    public void userModifyTest() throws Exception {

        // given
        String requestJson = objectMapper.writeValueAsString(
                new UserMyPageView(
                        "modifiedLast",
                        "modifiedFirst",
                        "modifiedNickPwd",
                        "modifiedNick",
                        "modifiedImg",
                        "modifiedCity"
                )
        );


        given(userMyPageService.userMyPageModify(
                any(LoginInfo.class), any(UserMyPageView.class)))
                .willReturn(
                        UserMyPageDto.builder()
                                .userLast("modifiedLast")
                                .userFirst("modifiedFirst")
                                .userNick("modifiedUserNick")
                                .userImg("modifiedUserImg")
                                .userCity("modifiedUserCity")
                                .build()
                );

        mockMvc.perform(post("/usr/mypage/modify")
                        .header(CommonConstant.ACCOUNT_TOKEN.getName(), "someToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(CommonConstant.ACCOUNT_TOKEN.getName()).description("JWT토큰")
                        ),
                        // then
                        requestFields(
                                fieldWithPath("userLast").type(JsonFieldType.STRING).description("회원 성"),
                                fieldWithPath("userFirst").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("userPwd").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                fieldWithPath("userNick").type(JsonFieldType.STRING).description("닉네임"),
                                fieldWithPath("userImg").type(JsonFieldType.STRING).description("회원 프로필 이미지"),
                                fieldWithPath("userCity").type(JsonFieldType.STRING).description("회원 거주지")
                        ),
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("result").type(JsonFieldType.STRING).description("결과"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        ))
                );
    }
}
