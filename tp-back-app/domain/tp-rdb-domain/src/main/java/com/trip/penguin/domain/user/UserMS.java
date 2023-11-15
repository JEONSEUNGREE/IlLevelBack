package com.trip.penguin.domain.user;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER_MS", schema = "tp-back-app")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_first", nullable = false)
    private String userFirst;

    @Column(name = "user_last", nullable = false)
    private String userLast;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_pwd", nullable = false)
    private String userPwd;

    @Column(name = "user_nick", nullable = false, length = 100)
    private String userNick;

    @Column(name = "user_img")
    private String userImg;

    @Column(name = "user_city")
    private String userCity;

    @Column(name = "social_provider")
    private String socialProvider;

    @Column(name = "social_provider_id")
    private String socialProviderId;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Column(name = "off_yn", nullable = false)
    private String offYn;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;
}