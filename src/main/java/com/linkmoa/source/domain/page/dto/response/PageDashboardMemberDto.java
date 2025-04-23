package com.linkmoa.source.domain.page.dto.response;

import com.linkmoa.source.domain.memberPageLink.constant.PermissionType;

import lombok.Builder;

@Builder
public record PageDashboardMemberDto(
	Long memberId,
	String email,
	String nickName,
	PermissionType permissionType
) {

}
