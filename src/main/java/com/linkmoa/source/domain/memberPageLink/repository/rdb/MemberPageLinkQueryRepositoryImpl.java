package com.linkmoa.source.domain.memberPageLink.repository.rdb;

import static com.linkmoa.source.domain.memberPageLink.entity.QMemberPageLink.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.page.contant.PageType;
import com.linkmoa.source.domain.page.entity.Page;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberPageLinkQueryRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public Optional<Page> findPersonalPageByMemberId(Long memberId) {
		return Optional.ofNullable(
			jpaQueryFactory
				.select(memberPageLink.page)
				.from(memberPageLink)
				.where(
					memberPageLink.member.id.eq(memberId),
					memberPageLink.page.pageType.eq(PageType.PERSONAL)
				)
				.fetchOne() // Page 또는 null 반환
		);
	}
}
