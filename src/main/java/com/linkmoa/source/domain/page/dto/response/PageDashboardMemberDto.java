package com.linkmoa.source.domain.page.dto.response;

import lombok.Builder;

@Builder
public record PageDashboardMemberDto(
	Long memberId,
	String email,
	String nickName,
	String colorCode,
	String role,
	boolean isWaiting
) {

}
