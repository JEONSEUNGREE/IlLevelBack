package com.trip.penguin.admin.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADMIN_MS")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id", nullable = false)
	private Long id;

	@Column(name = "admin_email", nullable = false)
	private String adminEmail;

	@Column(name = "admin_pwd", nullable = false)
	private String adminPwd;

	@Column(name = "admin_nm", nullable = false)
	private String adminNm;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;
}