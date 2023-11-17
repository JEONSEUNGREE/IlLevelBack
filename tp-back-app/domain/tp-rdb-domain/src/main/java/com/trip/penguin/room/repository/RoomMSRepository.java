package com.trip.penguin.room.repository;

import com.trip.penguin.room.domain.RoomMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomMSRepository extends JpaRepository<RoomMS, Long> {

    @Query(value = "select DISTINCT r from RoomMS r left join fetch r.roomPicList where r.id = :rId")
    List<RoomMS> getRoomPicListByRoomId(@Param("rId") Long rid);

    @Query(value = "select DISTINCT r from RoomMS r left join fetch r.reviewList where r.id = :rId")
    List<RoomMS> getReviewListByRoomId(@Param("rId") Long rid);
}
