package com.trip.penguin.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trip.penguin.review.domain.ReviewMS;
import com.trip.penguin.room.domain.RoomMS;

public interface ReviewMsRepository extends JpaRepository<ReviewMS, Long> {

	@Query(value = "select distinct r from RoomMS r "
		+ "left join fetch r.reviewList rv "
		+ "left join fetch rv.bookingMS rvb "
		+ "where rv.roomMS.id = :rvId")
	List<RoomMS> getReviewListUsingRoomJoin(@Param("rvId") Long rvId);

}
