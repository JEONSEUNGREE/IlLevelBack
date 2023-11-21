package com.trip.penguin.recommand.room.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRecDAO {

	private String roomNm;

	private String comName;

	private Integer sellPrc;

	private String couponYn;

	private String thumbNail;

	private Double ratingAvg;
}
