package com.linkmoa.source.domain.page.dto.request;

import com.linkmoa.source.domain.memberPageLink.constant.PermissionType;
import com.linkmoa.source.global.dto.request.BaseRequest;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class SharePageDashboardPermissionUpdateDto {

	public record Request(
		BaseRequest baseRequest,
		@NotNull Long targetMemberId,             // 권한 변경 또는 내보내기 대상 사용자의 ID
		@Nullable PermissionType permissionType    // 설정할 권한 (null일 경우 내보내기로 간주)
	) {

	}

	@Builder
	public record Response(
		Long targetMemberId,
		String targetMemberEmail,
		String message

	) {

	}
}
