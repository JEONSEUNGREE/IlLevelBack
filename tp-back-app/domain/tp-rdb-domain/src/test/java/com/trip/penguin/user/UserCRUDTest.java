package com.trip.penguin.user;


import com.trip.penguin.follow.service.FollowService;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(value = "com.trip.penguin")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserCRUDTest {

    private final UserService userService;

    private final FollowService followService;

    private final EntityManager em;

    private UserMS beforeCommitUser;


    @Autowired
    public UserCRUDTest(UserService userService, FollowService followService, EntityManager em) {
        this.followService = followService;
        this.userService = userService;
        this.em = em;
    }

    @BeforeEach
    public void beforeTest() {
        /* 회원 가입 정보 */
        beforeCommitUser = UserMS.builder()
                .offYn("N")
                .userCity("Seoul")
                .userImg("default")
                .userEmail("test@email.com")
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
        // given

        /* 회원 가입 */
        UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);

        em.flush();
        em.clear();

        // when
        /* 회원 조회 */
        UserMS findUser = userService.getUser(afterCommitUser).orElseThrow(IllegalArgumentException::new);

        // then
        /* 회원 검증 */
        assertEquals(beforeCommitUser.getId(), findUser.getId());
    }

    @DisplayName("회원 정보 수정 테스트")
    @Test
    void updateTest() {

        // given
        /* 회원 정보 가입 */
        UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);

        em.flush();
        em.clear();

        // when
        /* 회원 조회 */
        UserMS findUser = userService.getUser(afterCommitUser).orElseThrow(IllegalArgumentException::new);

        /* 회원 정보 update */
        findUser.setUserEmail("change@change.com");
        UserMS updateUser = userService.signUpUser(findUser);

        em.flush();
        em.clear();

        // then
        /* 회원 조회 */
        UserMS updateConfirmUser = userService.getUser(afterCommitUser).orElseThrow(IllegalArgumentException::new);
        /* 회원 검증 */
        assertEquals(afterCommitUser.getUserEmail(), "test@email.com");
        assertEquals(updateConfirmUser.getUserEmail(), "change@change.com");
    }

    @DisplayName("회원 탈퇴 테스트")
    @Test
    void deleteTest() {
        // given
        /* 회원 가입 */
        UserMS afterCommitUser = userService.signUpUser(beforeCommitUser);

        em.flush();
        em.clear();

        // when
        /* 회원 조회 */
        UserMS findUser = userService.getUser(afterCommitUser).orElseThrow(IllegalArgumentException::new);
        /* 회원 삭제 */
        userService.deleteUser(findUser);

        em.flush();
        em.clear();

        // then
        /* 회원 탈퇴 여부 */
        assertFalse(userService.getUser(afterCommitUser).isPresent());

    }

}
