package com.linkmoa.source.domain.member.repository.rdb;

import static com.linkmoa.source.domain.member.entity.QMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberQueryDslRepository {

	private final JPAQueryFactory jpaQueryFactory;

	public List<MemberSimpleResponse> searchByKeyword(String keyword) {
		return jpaQueryFactory
			.select(Projections.constructor(MemberSimpleResponse.class,
				member.id,
				member.email,
				member.nickname,
				member.colorCode
			))
			.from(member)
			.where(
				member.email.containsIgnoreCase(keyword)
					.or(member.nickname.containsIgnoreCase(keyword))
			)
			.fetch();
	}

}
