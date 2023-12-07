package com.trip.penguin.user.repository;

import static com.trip.penguin.follow.domain.QFollowMS.*;
import static com.trip.penguin.user.domain.QUserMS.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trip.penguin.follow.dto.UserFollowDTO;
import com.trip.penguin.follow.view.UserFollowSchCdtView;

@Repository
public class UserFollowCustomRepositoryImpl implements UserFollowCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Autowired
	public UserFollowCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
		this.queryFactory = jpaQueryFactory;
	}

	@Override
	public List<UserFollowDTO> getUserFollowList(Long userId, UserFollowSchCdtView userFollowSchCdtView) {

		Pageable pageable = PageRequest.of(userFollowSchCdtView.getCurPage(), userFollowSchCdtView.getPageSize());

		return queryFactory.select(Projections.fields(UserFollowDTO.class, followMS.followUser.id.as("followId"),
				followMS.followUser.userNick, followMS.followUser.userImg))
			.from(followMS)
			.innerJoin(followMS.followUser, userMS)
			.where(followMS.userMS.id.eq(userId))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.fetch();
	}

	@Override
	public Integer getUserFollowCount(Long userId) {
		return queryFactory.select(followMS.count())
			.from(followMS)
			.where(followMS.userMS.id.eq(userId)).fetchFirst().intValue();
	}

	@Override
	public Integer getUserFollowerCount(Long userId) {
		return queryFactory.select(followMS.count())
			.from(followMS)
			.where(followMS.followUser.id.eq(userId)).fetchFirst().intValue();
	}
}
