package com.linkmoa.source.domain.page.repository.rdb;

import static com.linkmoa.source.domain.dispatch.entity.QSharePageInvitationRequest.*;
import static com.linkmoa.source.domain.member.entity.QMember.*;
import static com.linkmoa.source.domain.memberPageLink.entity.QMemberPageLink.*;
import static com.linkmoa.source.domain.page.entity.QPage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.page.contant.PageVisibility;
import com.linkmoa.source.domain.page.dto.response.PageDashboardMemberDto;
import com.linkmoa.source.domain.page.dto.response.PageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PageQueryDslRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public List<PageResponse> findAllPagesByMemberId(Long memberId) {

		List<PageResponse> result = jpaQueryFactory.
			select(Projections.constructor(
				PageResponse.class,
				page.id,
				page.pageTitle,
				page.pageType
			))
			.from(memberPageLink)
			.where(memberIdEq(memberId))
			.join(page).on(memberPageLink.page.id.eq(page.id))
			.fetch();
		return result;
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId == null ? null : memberPageLink.member.id.eq(memberId);
	}

	public Long findRootDirectoryIdByPageId(Long pageId) {
		return jpaQueryFactory.
			select(page.rootDirectory.id)
			.from(page)
			.where(page.id.eq(pageId))
			.fetchOne();
	}

	public List<PageDashboardMemberDto> findDashboardMembersByPageId(Long pageId) {
		return jpaQueryFactory
			.select(Projections.constructor(
				PageDashboardMemberDto.class,
				member.id,
				member.email,
				member.nickname,
				memberPageLink.permissionType.stringValue(),
				Expressions.constant(false)
			))
			.from(memberPageLink)
			.join(memberPageLink.member, member)
			.where(memberPageLink.page.id.eq(pageId))
			.fetch();
	}

	public List<PageDashboardMemberDto> findWaitingInvitedMembersByPageId(Long pageId) {
		return jpaQueryFactory
			.select(Projections.constructor(
				PageDashboardMemberDto.class,
				sharePageInvitationRequest.receiver.id,
				sharePageInvitationRequest.receiver.email,
				sharePageInvitationRequest.receiver.nickname,
				Expressions.constant("UNKNOWN"),
				Expressions.constant(true)
			))
			.from(sharePageInvitationRequest)
			.where(
				sharePageInvitationRequest.page.id.eq(pageId),
				sharePageInvitationRequest.requestStatus.eq(RequestStatus.WAITING)
			)
			.fetch();
	}

	public PageVisibility findPageVisibilityByPageId(Long pageId) {
		return jpaQueryFactory
			.select(page.visibility)
			.from(page)
			.where(page.id.eq(pageId))
			.fetchOne();
	}

}
