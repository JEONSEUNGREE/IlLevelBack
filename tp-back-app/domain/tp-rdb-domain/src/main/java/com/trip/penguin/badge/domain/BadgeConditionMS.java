package com.trip.penguin.badge.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BADGE_CONDITION_MS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeConditionMS {

	@Id
	@Column(name = "bg_cdt_Id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "badge_id", nullable = false)
	private BadgeMS badgeMS;

	@Column(name = "cdt_nm")
	private String cdtNm;

	@Column(name = "cdt_cnt", nullable = false)
	private Integer cdtCnt;

}