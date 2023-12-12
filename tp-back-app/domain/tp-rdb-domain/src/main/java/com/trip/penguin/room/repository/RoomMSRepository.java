package com.trip.penguin.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trip.penguin.room.domain.RoomMS;

import jakarta.persistence.LockModeType;

public interface RoomMSRepository extends JpaRepository<RoomMS, Long> {

	@Query(value = "select DISTINCT r from RoomMS r left join fetch r.reviewList where r.id = :rId")
	List<RoomMS> getReviewListByRoomId(@Param("rId") Long rid);

	@Query(value = "select DISTINCT r from RoomMS r left join fetch r.roomPicList where r.id = :rId")
	RoomMS getRoomMsAndRoomPicByRoomId(@Param("rId") Long rid);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select r from RoomMS r where r.id = :rId")
	RoomMS findByRoomId(@Param("rId") Long rId);
}
