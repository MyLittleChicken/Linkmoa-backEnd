package com.linkmoa.source.domain.dispatch.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NotificationSenderInfo(
	String email,
	String nickname,
	String colorCode,
	LocalDateTime sentAt
) {
}
