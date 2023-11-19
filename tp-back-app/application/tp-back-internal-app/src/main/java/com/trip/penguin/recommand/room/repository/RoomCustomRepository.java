package com.trip.penguin.recommand.room.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.trip.penguin.room.domain.RoomMS;

public interface RoomCustomRepository {

	List<RoomMS> getMainRecRoom(Pageable pageable);
}
