package com.trip.penguin.follow.domain;

import com.trip.penguin.user.domain.UserMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FOLLOW_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowMS {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

    @Column(name = "follower_id", nullable = false)
    private Long followerId;

}