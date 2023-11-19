package com.trip.penguin.recommand.room.repository;

import static com.trip.penguin.room.domain.QRoomMS.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trip.penguin.room.domain.RoomMS;

@Repository
public class RoomCustomRepositoryImpl implements RoomCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Autowired
	public RoomCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.jpaQueryFactory = jpaQueryFactory;
	}

	@Override
	public List<RoomMS> getMainRecRoom(Pageable pageable) {

		List<RoomMS> fetch = jpaQueryFactory.selectFrom(roomMS).fetch();

		return null;
	}
}
