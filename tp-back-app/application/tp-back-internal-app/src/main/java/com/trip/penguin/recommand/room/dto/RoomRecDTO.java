package com.trip.penguin.recommand.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRecDTO {

	private String roomNm;

	private String comNm;

	private Long sellPrc;

	private String couponYn;

	private String thumbNail;

	private Long rating;

}
