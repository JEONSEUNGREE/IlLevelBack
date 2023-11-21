package com.trip.penguin.alert.domain;

import java.time.LocalDate;

import com.trip.penguin.user.domain.UserMS;

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
@Table(name = "ALERT_MS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertMS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alert_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserMS userMs;

	@Column(name = "alert_title", nullable = false)
	private String alertTitle;

	@Column(name = "alert_contents", nullable = false)
	private String alertContents;

	@Column(name = "alert_read", nullable = false)
	private Integer alertRead;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

}