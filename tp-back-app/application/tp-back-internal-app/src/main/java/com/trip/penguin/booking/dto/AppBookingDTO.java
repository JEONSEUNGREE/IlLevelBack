package com.trip.penguin.booking.dto;

import java.time.LocalDateTime;

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

}
