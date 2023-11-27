package com.trip.penguin.cs.domain;

import java.time.LocalDateTime;

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
import lombok.Setter;

@Entity
@Table(name = "CS_MS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsMS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cs_id", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserMS userMS;

	@Column(name = "csq_title", nullable = false, length = 100)
	private String csqTitle;

	@Column(name = "csq_content", nullable = false, length = 1000)
	private String csqContent;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	@Column(name = "csq_status", nullable = false)
	private Integer csqStatus;

	@Column(name = "csa_title", length = 100)
	private String csaTitle;

	@Column(name = "csa_content", length = 1000)
	private String csaContent;

	@Column(name = "admin_nm")
	private String adminNm;

	public void createCsMs(UserMS userMS) {
		this.createdDate = LocalDateTime.now();
		this.modifiedDate = LocalDateTime.now();
		this.csqStatus = 0;
		this.userMS = userMS;
	}

}