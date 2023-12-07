package com.trip.penguin.review.domain;

import java.time.LocalDateTime;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.user.domain.UserMS;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REVIEW_MS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewMS {

	@Id
	@Column(name = "review_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserMS userMS;

	@OneToOne(mappedBy = "reviewMS", fetch = FetchType.LAZY)
	private BookingMS bookingMS;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id", nullable = false)
	private RoomMS roomMS;

	@Column(name = "re_title", nullable = false, length = 100)
	private String reTitle;

	@Column(name = "re_content", nullable = false, length = 1000)
	private String reContent;

	@Column(name = "rating", nullable = false)
	private Integer rating;

	@Column(name = "re_accom", length = 1000)
	private String reAccom;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDateTime modifiedDate;

	@Column(name = "report", nullable = false)
	private String report;

	public void createReview(BookingMS bookingMS, UserMS userMS) {
		userMS.addReview(this);
		bookingMS.setReviewMS(this);
		this.setBookingMS(bookingMS);
		this.setRoomMS(bookingMS.getRoom());
		this.setReport(CommonConstant.N.getName());

		this.setModifiedDate(LocalDateTime.now());
		this.setCreatedDate(LocalDateTime.now());
	}

	public void checkRating() {
		if (this.rating > 5) {
			throw new RuntimeException("5점이상 불가");
		}
	}

}