package com.trip.penguin.recommand.room.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainRecRoomSchCdt {

	private Integer pageNumber;

	private Integer pageSize;

	private Object mainViewData;

	private Object mainSubData;
}
