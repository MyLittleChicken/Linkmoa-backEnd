package com.linkmoa.source.domain.dispatch.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.entity.SharePageInvitationRequest;
import com.linkmoa.source.domain.dispatch.repository.SharePageInvitationRequestDataAccess;
import com.linkmoa.source.domain.dispatch.repository.rdb.SharePageInvitationRequestJpaRepository;
import com.linkmoa.source.domain.dispatch.repository.rdb.SharePageInvitationRequestQueryDslRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SharePageInvitationRequestDataAccessImpl implements SharePageInvitationRequestDataAccess {

	private final SharePageInvitationRequestJpaRepository sharePageInvitationRequestJpaRepository;
	private final SharePageInvitationRequestQueryDslRepositoryImpl sharePageInvitationRequestQueryDslRepository;

	@Override
	public List<DispatchDetailResponse> findAllSharePageInvitationsByReceiverEmail(String receiverEmail) {
		return sharePageInvitationRequestQueryDslRepository.findAllSharePageInvitationsByReceiverEmail(receiverEmail);
	}

	@Override
	public Optional<SharePageInvitationRequest> findByPageIdAndRequestStatus(Long pageId, RequestStatus requestStatus) {
		return sharePageInvitationRequestJpaRepository.findByPageIdAndRequestStatus(pageId, requestStatus);
	}

	@Override
	public SharePageInvitationRequest save(SharePageInvitationRequest sharePageInvitationRequest) {
		return sharePageInvitationRequestJpaRepository.save(sharePageInvitationRequest);
	}

	@Override
	public Optional<SharePageInvitationRequest> findById(Long requestId) {
		return sharePageInvitationRequestJpaRepository.findById(requestId);
	}
}
