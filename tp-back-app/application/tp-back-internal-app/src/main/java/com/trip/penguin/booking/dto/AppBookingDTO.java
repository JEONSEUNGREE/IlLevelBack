package com.trip.penguin.booking.dto;

import java.time.LocalDateTime;

import com.trip.penguin.booking.domain.BookingMS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AppBookingDTO {

	private Long id;

	private Long roomId;

	private String bookNm;

	private String payMethod;

	private Integer payAmount;

	private Integer sellPrc;

	private Integer count;

	private String couponYn;

	private LocalDateTime checkIn;

	private LocalDateTime checkOut;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	public AppBookingDTO changeDTO(BookingMS bookingMS) {
		this.id = bookingMS.getId();
		this.roomId = bookingMS.getRoom().getId();
		this.bookNm = bookingMS.getBookNm();
		this.payMethod = bookingMS.getPayMethod();
		this.payAmount = bookingMS.getPayAmount();
		this.sellPrc = bookingMS.getSellPrc();
		this.count = bookingMS.getCount();
		this.couponYn = bookingMS.getCouponYn();
		this.checkIn = bookingMS.getCheckIn();
		this.checkOut = bookingMS.getCheckOut();
		this.createdDate = bookingMS.getCreateDate();
		this.modifiedDate = bookingMS.getModifiedDate();
		return this;
	}

}
