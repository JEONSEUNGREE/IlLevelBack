package com.trip.penguin.domain.user;

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
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

    @Column(name = "follower_id", nullable = false)
    private String followerId;

}