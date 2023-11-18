package com.trip.penguin.pay.domain;

import java.time.LocalDateTime;

import com.trip.penguin.booking.domain.BookingMS;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAY_STATUS_MS", schema = "tp-back-app")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayStatusMS {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "book_id", nullable = false)
	private BookingMS bookingMs;

	@Column(name = "book_status", nullable = false)
	private Integer bookStatus;

	@Column(name = "create_date")
	private LocalDateTime createDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

}