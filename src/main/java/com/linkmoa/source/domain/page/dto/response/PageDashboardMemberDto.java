package com.linkmoa.source.domain.page.dto.response;

public record PageDashboardMemberDto(
	Long memberId,
	String email,
	String nickName,
	String colorCode,
	String role,
	Boolean isWaiting
) {

}
