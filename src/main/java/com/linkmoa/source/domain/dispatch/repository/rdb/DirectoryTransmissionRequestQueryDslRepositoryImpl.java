package com.linkmoa.source.domain.dispatch.repository.rdb;

import static com.linkmoa.source.domain.dispatch.entity.QDirectoryTransmissionRequest.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DirectoryTransmissionRequestQueryDslRepositoryImpl {

	private final JPAQueryFactory jpaQueryFactory;

	public List<DispatchDetailResponse> findAllDirectoryTransmissionRequestByReceiverEmail(String receiverEmail) {

		List<DispatchDetailResponse> result = jpaQueryFactory.select(
				Projections.constructor(
					DispatchDetailResponse.class,
					directoryTransmissionRequest.id,
					directoryTransmissionRequest.sender.email,
					directoryTransmissionRequest.requestStatus,
					directoryTransmissionRequest.notificationType
				))
			.from(directoryTransmissionRequest)
			.where(receiverEmailEq(receiverEmail))
			.fetch();

		return result;
	}

	private BooleanExpression receiverEmailEq(String receiverEmail) {
		return receiverEmail == null ? null : directoryTransmissionRequest.receiver.email.eq(receiverEmail);
	}
}
