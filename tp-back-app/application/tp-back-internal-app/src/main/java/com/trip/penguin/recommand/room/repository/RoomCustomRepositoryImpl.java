package com.trip.penguin.recommand.room.repository;

import static com.trip.penguin.review.domain.QReviewMS.*;
import static com.trip.penguin.room.domain.QRoomMS.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.recommand.room.dao.RoomRecDAO;
import com.trip.penguin.recommand.room.view.MainRecRoomSchCdt;

@Repository
public class RoomCustomRepositoryImpl implements RoomRecCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Autowired
	public RoomCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.queryFactory = jpaQueryFactory;
	}

	@Override
	public List<RoomRecDAO> getMainRecRoomListWithPaging(MainRecRoomSchCdt mainRecRoomSchCdt) {

		Pageable pageable = PageRequest.of(mainRecRoomSchCdt.getPageNumber(), mainRecRoomSchCdt.getPageSize());

		return queryFactory.selectDistinct(Projections.fields(RoomRecDAO.class,
				roomMS.roomNm, roomMS.comName, roomMS.sellPrc,
				roomMS.couponYn, roomMS.thumbNail, reviewMS.rating.avg().as("ratingAvg"), reviewMS.count().as("reviewCount")))
			.from(roomMS)
			.where(roomMS.soldOutYn.eq(CommonConstant.N.name()))
			.leftJoin(roomMS.reviewList, reviewMS)
			.groupBy(roomMS.id)
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.fetch();
	}

}
