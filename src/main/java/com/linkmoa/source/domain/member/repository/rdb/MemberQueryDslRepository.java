package com.linkmoa.source.domain.member.repository.rdb;

import static com.linkmoa.source.domain.member.entity.QMember.*;
import static com.linkmoa.source.domain.memberPageLink.entity.QMemberPageLink.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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

	public List<MemberSimpleResponse> findMembersBySharePageId(Long pageId) {
		return jpaQueryFactory
			.select(Projections.constructor(MemberSimpleResponse.class,
				member.id,
				member.email,
				member.nickname,
				member.colorCode
			))
			.from(member)
			.join(
				member.memberPageLinks, memberPageLink
			)
			.where(memberPageLink.page.id.eq(pageId))
			.orderBy(
				Expressions.numberTemplate(Integer.class,
					"case {0} " +
						"when 'ADMIN' then 1 " +
						"when 'HOST' then 2 " +
						"when 'EDITOR' then 3 " +
						"when 'VIEWER' then 4 " +
						"else 99 end", memberPageLink.permissionType.stringValue()
				).asc(),
				member.nickname.asc()
			)
			.fetch();
	}
}
