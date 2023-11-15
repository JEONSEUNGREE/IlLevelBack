package com.trip.penguin.badge.domain;

import com.trip.penguin.room.domain.RoomPicMS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BAGDE_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "badgeMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BadgeConditionMS> badgeConditionList = new ArrayList<>();

    @Column(name = "badge_nm", nullable = false)
    private String badgeNm;

    @Column(name = "badge_img", nullable = false)
    private String badgeImg;

    @Column(name = "badge_lv", nullable = false)
    private Integer badgeLv;

}