package com.linkmoa.source.domain.dispatch.dto.response;

import com.linkmoa.source.domain.dispatch.constant.RequestStatus;
import com.linkmoa.source.domain.notification.constant.NotificationType;

public record DispatchSimpleResponse(
	Long id,
	RequestStatus requestStatus,
	String senderEmail,
	NotificationType notificationType
) {
	public static DispatchSimpleResponse of(Long id, RequestStatus status, String senderEmail, NotificationType type) {
		return new DispatchSimpleResponse(id, status, senderEmail, type);
	}
}


