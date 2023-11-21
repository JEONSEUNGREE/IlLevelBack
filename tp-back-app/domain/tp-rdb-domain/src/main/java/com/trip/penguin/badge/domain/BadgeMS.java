package com.trip.penguin.badge.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BAGDE_MS")
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