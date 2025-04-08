package com.linkmoa.source.domain.dispatch.repository.rdb;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.dispatch.entity.DirectoryTransmissionRequest;

public interface DirectoryTransmissionRequestJpaRepository
	extends JpaRepository<DirectoryTransmissionRequest, Long> {

	Optional<DirectoryTransmissionRequest> findByDirectoryIdAndRequestStatus(Long directoryId,
		RequestStatus requestStatus);
}
