package com.linkmoa.source.domain.dispatch.dto.response;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.notification.constant.NotificationType;

import lombok.Builder;

@Builder
public record DispatchDetailResponse(
	Long id,
	NotificationSenderInfo senderInfo,
	RequestStatus requestStatus,
	NotificationType notificationType
) {
}
