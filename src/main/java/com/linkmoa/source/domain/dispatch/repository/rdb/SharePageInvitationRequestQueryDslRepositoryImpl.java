package com.linkmoa.source.domain.dispatch.repository.rdb;

import static com.linkmoa.source.domain.dispatch.entity.QSharePageInvitationRequest.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.dto.response.NotificationSenderInfo;
import com.linkmoa.source.domain.dispatch.dto.response.SharePageInvitationRequestRawResult;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SharePageInvitationRequestQueryDslRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public List<DispatchDetailResponse> findAllSharePageInvitationsByReceiverEmail(String receiverEmail) {

		List<SharePageInvitationRequestRawResult> rawResults = jpaQueryFactory.select(
				Projections.constructor(
					SharePageInvitationRequestRawResult.class,
					sharePageInvitationRequest.id,
					sharePageInvitationRequest.sender.email,
					sharePageInvitationRequest.sender.nickname,
					sharePageInvitationRequest.sender.colorCode,
					sharePageInvitationRequest.createdAt,
					sharePageInvitationRequest.requestStatus,
					sharePageInvitationRequest.notificationType,
					sharePageInvitationRequest.page.pageTitle
				))
			.from(sharePageInvitationRequest)
			.where(receiverEmailEq(receiverEmail))
			.fetch();

		return rawResults.stream()
			.map(raw -> new DispatchDetailResponse(
				raw.id(),
				new NotificationSenderInfo(
					raw.email(),
					raw.nickname(),
					raw.colorCode(),
					raw.sentAt()
				),
				raw.requestStatus(),
				raw.notificationType(),
				String.format("%s(%s)님이 회원님에게 %s 페이지에 초대했습니다.", raw.nickname(), raw.email(), raw.pageTitle())

			)).toList();
	}

	private BooleanExpression receiverEmailEq(String receiverEmail) {
		return receiverEmail == null ? null : sharePageInvitationRequest.receiver.email.eq(receiverEmail);
	}
}
