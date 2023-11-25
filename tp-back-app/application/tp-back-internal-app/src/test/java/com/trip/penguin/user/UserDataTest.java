package com.trip.penguin.user;


import com.trip.penguin.TpBackInternalApp;
import com.trip.penguin.config.TestContainer;
import com.trip.penguin.recommand.room.repository.RoomRecCustomRepository;
import com.trip.penguin.room.service.RoomService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@ContextConfiguration(classes = {TpBackInternalApp.class})
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(basePackages = {
        "com.trip.penguin.room",
        "com.trip.penguin.user",
        "com.trip.penguin.recommand.room.repository",
        "com.trip.penguin.recommand.room.service"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Import(TestContainer.class)
public class UserDataTest {

    @Autowired
    private RoomRecCustomRepository roomRecService;

    @Autowired
    private UserService userService;

    @Autowired
    RoomService roomService;

    private UserMS beforeCommitUser;

    @BeforeEach
    public void beforeData() {

        /* 회원 가입 정보 */
        beforeCommitUser = UserMS.builder()
                .offYn("N")
                .userCity("Seoul")
                .userImg("default")
                .userEmail("test@mail.com")
                .userRole("user")
                .userNick("default")
                .userPwd("test")
                .userFirst("t")
                .userLast("est")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    @DisplayName("기본 회원 가입 테스트")
    @Test
    void signupTest() {

        userService.signUpUser(beforeCommitUser);



    }
}
