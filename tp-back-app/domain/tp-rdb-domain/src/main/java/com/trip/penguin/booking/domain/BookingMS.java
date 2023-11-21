package com.trip.penguin.booking.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.trip.penguin.pay.domain.PayStatusMS;
import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.user.domain.UserMS;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BOOKING_MS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingMS {

	@Id
	@Column(name = "book_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "bookingMs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PayStatusMS> payStatusList = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private ReviewMS reviewMS;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "room_id", nullable = false)
	private RoomMS room;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private UserMS userMS;

	@Column(name = "book_nm", nullable = false)
	private String bookNm;

	@Column(name = "check_in", nullable = false)
	private LocalDateTime checkIn;

	@Column(name = "check_out", nullable = false)
	private LocalDateTime checkOut;

	@Column(name = "pay_method", nullable = false)
	private String payMethod;

	@Column(name = "sell_prc", nullable = false)
	private Integer sellPrc;

	@Column(name = "pay_amount", nullable = false)
	private Integer payAmount;

	@Column(name = "coupon_yn", nullable = false)
	private String couponYn;

	@Column(name = "create_date", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	public void createBookingMs() {
		this.setModifiedDate(LocalDateTime.now());
		this.setCreateDate(LocalDateTime.now());
	}
}