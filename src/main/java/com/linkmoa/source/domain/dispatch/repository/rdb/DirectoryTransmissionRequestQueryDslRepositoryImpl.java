package com.linkmoa.source.domain.dispatch.repository.rdb;

import static com.linkmoa.source.domain.dispatch.entity.QDirectoryTransmissionRequest.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.dto.response.DirectoryTransmissionRequestRawResult;
import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.dto.response.NotificationSenderInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DirectoryTransmissionRequestQueryDslRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public List<DispatchDetailResponse> findAllDirectoryTransmissionRequestByReceiverEmail(String receiverEmail) {

		List<DirectoryTransmissionRequestRawResult> rawResults = jpaQueryFactory.select(
				Projections.constructor(
					DirectoryTransmissionRequestRawResult.class,
					directoryTransmissionRequest.id,
					directoryTransmissionRequest.sender.email,
					directoryTransmissionRequest.sender.nickname,
					directoryTransmissionRequest.sender.colorCode,
					directoryTransmissionRequest.createdAt,
					directoryTransmissionRequest.requestStatus,
					directoryTransmissionRequest.notificationType,
					directoryTransmissionRequest.directory.directoryName
				))
			.from(directoryTransmissionRequest)
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
				String.format("%s(%s)님이 회원님에게 %s디렉토리를 전송했습니다.", raw.nickname(), raw.email(), raw.directoryName())
			)).toList();
	}

	private BooleanExpression receiverEmailEq(String receiverEmail) {
		return receiverEmail == null ? null : directoryTransmissionRequest.receiver.email.eq(receiverEmail);
	}
}
