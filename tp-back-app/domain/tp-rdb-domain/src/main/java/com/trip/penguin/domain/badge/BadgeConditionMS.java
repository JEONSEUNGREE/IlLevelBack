package com.trip.penguin.domain.badge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BADGE_CONDITION_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeConditionMS {

    @Id
    @Column(name = "bg_cdt_Id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "badge_id", nullable = false)
    private BadgeMS badgeMS;

    @Column(name = "cdt_nm")
    private String cdtNm;

    @Column(name = "cdt_cnt", nullable = false)
    private Integer cdtCnt;

}