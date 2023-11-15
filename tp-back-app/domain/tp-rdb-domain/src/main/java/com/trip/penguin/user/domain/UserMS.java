package com.trip.penguin.user.domain;
import com.trip.penguin.alert.domain.AlertMS;
import com.trip.penguin.badge.domain.UserBadge;
import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.follow.domain.FollowMS;
import com.trip.penguin.wish.domain.WishlistMS;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER_MS", schema = "tp-back-app")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;


    @OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FollowMS> followList = new ArrayList<>();

    @OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserBadge> userBadgeList = new ArrayList<>();

    @OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WishlistMS> wishlistList = new ArrayList<>();

    @OneToMany(mappedBy = "userMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CsMS> csList = new ArrayList<>();

    @OneToMany(mappedBy = "userMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlertMS> alertList = new ArrayList<>();

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