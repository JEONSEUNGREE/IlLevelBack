package com.trip.penguin.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trip.penguin.room.domain.RoomMS;

public interface RoomMSRepository extends JpaRepository<RoomMS, Long> {

	@Query(value = "select DISTINCT r from RoomMS r left join fetch r.reviewList where r.id = :rId")
	List<RoomMS> getReviewListByRoomId(@Param("rId") Long rid);
}
