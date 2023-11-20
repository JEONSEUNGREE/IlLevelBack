package com.trip.penguin.recommand.room.view;

import org.springframework.data.domain.Pageable;

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

	private Pageable pageable;

	private Object mainViewData;

	private Object mainSubData;
}
