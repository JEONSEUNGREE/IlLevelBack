package com.trip.penguin.recommand.room.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
