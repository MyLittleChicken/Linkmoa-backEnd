package com.linkmoa.source.domain.dispatch.dto.response;

import java.time.LocalDateTime;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.notification.constant.NotificationType;

public record DirectoryTransmissionRequestRawResult(
	Long id,
	String email,
	String nickname,
	String colorCode,
	LocalDateTime sentAt,
	String message,
	RequestStatus requestStatus,
	NotificationType notificationType
) {
}
