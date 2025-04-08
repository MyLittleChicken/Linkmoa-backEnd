package com.linkmoa.source.domain.favorite.repository.rdb;

import static com.linkmoa.source.domain.favorite.entity.QFavorite.*;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FavoriteQueryDslRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public void incrementOrderIndexesForMember(Member member) {
		jpaQueryFactory.update(favorite)
			.set(favorite.orderIndex, favorite.orderIndex.add(1))
			.where(favorite.member.eq(member))
			.execute();
	}

	public void decrementFavoriteOrderIndexes(Integer orderIndex) {
		jpaQueryFactory.update(favorite)
			.set(favorite.orderIndex, favorite.orderIndex.subtract(1))
			.where(favorite.orderIndex.gt(orderIndex))
			.execute();
	}
}
