package com.linkmoa.source.domain.dispatch.repository.rdb;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.entity.SharePageInvitationRequest;

public interface SharePageInvitationRequestJpaRepository
	extends JpaRepository<SharePageInvitationRequest, Long> {

	Optional<SharePageInvitationRequest> findByPageIdAndRequestStatus(Long pageId, RequestStatus requestStatus);
}
