package com.linkmoa.source.domain.dispatch.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.dto.response.DispatchDetailResponse;
import com.linkmoa.source.domain.dispatch.entity.DirectoryTransmissionRequest;
import com.linkmoa.source.domain.dispatch.repository.DirectoryTransmissionRequestDataAccess;
import com.linkmoa.source.domain.dispatch.repository.rdb.DirectoryTransmissionRequestJpaRepository;
import com.linkmoa.source.domain.dispatch.repository.rdb.DirectoryTransmissionRequestQueryDslRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DirectoryTransmissionRequestDataAccessImpl implements DirectoryTransmissionRequestDataAccess {

	private final DirectoryTransmissionRequestQueryDslRepositoryImpl directoryTransmissionRequestQueryDslRepository;
	private final DirectoryTransmissionRequestJpaRepository directoryTransmissionRequestJpaRepository;

	@Override
	public List<DispatchDetailResponse> findAllDirectoryTransmissionRequestByReceiverEmail(String receiverEmail) {
		return directoryTransmissionRequestQueryDslRepository.findAllDirectoryTransmissionRequestByReceiverEmail(
			receiverEmail);
	}

	@Override
	public Optional<DirectoryTransmissionRequest> findByDirectoryIdAndRequestStatus(Long directoryId,
		RequestStatus requestStatus) {
		return directoryTransmissionRequestJpaRepository.findByDirectoryIdAndRequestStatus(
			directoryId, requestStatus);
	}

	@Override
	public DirectoryTransmissionRequest save(DirectoryTransmissionRequest directoryTransmissionRequest) {
		return directoryTransmissionRequestJpaRepository.save(directoryTransmissionRequest);
	}

	@Override
	public Optional<DirectoryTransmissionRequest> findById(Long requestId) {
		return directoryTransmissionRequestJpaRepository.findById(requestId);
	}
}
