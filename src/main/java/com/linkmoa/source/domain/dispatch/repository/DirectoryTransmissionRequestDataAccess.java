package com.linkmoa.source.domain.dispatch.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.entity.DirectoryTransmissionRequest;

public interface DirectoryTransmissionRequestDataAccess {
	List<DispatchDetailResponse> findAllDirectoryTransmissionRequestByReceiverEmail(String receiverEmail);

	Optional<DirectoryTransmissionRequest> findByDirectoryIdAndRequestStatus(Long directoryId,
		RequestStatus requestStatus);

	DirectoryTransmissionRequest save(DirectoryTransmissionRequest directoryTransmissionRequest);

	Optional<DirectoryTransmissionRequest> findById(Long requestId);

}
