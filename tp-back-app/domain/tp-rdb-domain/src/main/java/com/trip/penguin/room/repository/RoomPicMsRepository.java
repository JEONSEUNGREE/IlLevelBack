package com.trip.penguin.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.domain.RoomPicMS;

public interface RoomPicMsRepository extends JpaRepository<RoomPicMS, Long> {

	@Query(value = "select p from RoomPicMS p left join fetch p.roomMs where p.roomMs = :roomMs")
	List<RoomPicMS> getRoomPicListByRoomMs(@Param("roomMs") RoomMS roomMS);

	@Query(value = "select p from RoomPicMS p left join fetch p.roomMs where p.picSeq = 0 and p.roomMs = :roomMs")
	RoomPicMS getRoomThumbnailByRoomMs(@Param("roomMs") RoomMS roomMS);
}
