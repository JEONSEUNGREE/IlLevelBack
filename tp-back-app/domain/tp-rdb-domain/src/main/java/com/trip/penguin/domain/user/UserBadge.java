package com.trip.penguin.domain.user;

import com.trip.penguin.domain.badge.BadgeMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_BADGE", schema = "tp-back-app")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBadge {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserMS userMS;

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private BadgeMS badgeMS;

}
