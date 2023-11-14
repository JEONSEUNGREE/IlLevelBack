package com.trip.penguin.domain.badge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "badge_nm", nullable = false)
    private String badgeNm;

    @Column(name = "badge_img", nullable = false)
    private String badgeImg;

    @Column(name = "badge_lv", nullable = false)
    private Integer badgeLv;

}