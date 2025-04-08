package com.linkmoa.source.domain.dispatch.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.entity.SharePageInvitationRequest;

public interface SharePageInvitationRequestDataAccess {
	List<DispatchDetailResponse> findAllSharePageInvitationsByReceiverEmail(String receiverEmail);

	Optional<SharePageInvitationRequest> findByPageIdAndRequestStatus(Long pageId, RequestStatus requestStatus);

	SharePageInvitationRequest save(SharePageInvitationRequest sharePageInvitationRequest);

	Optional<SharePageInvitationRequest> findById(Long requestId);
}
