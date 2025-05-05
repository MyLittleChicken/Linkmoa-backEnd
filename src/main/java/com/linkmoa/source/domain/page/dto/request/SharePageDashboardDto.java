package com.linkmoa.source.domain.page.dto.request;

import java.util.List;

import com.linkmoa.source.domain.page.contant.PageType;
import com.linkmoa.source.domain.page.contant.PageVisibility;
import com.linkmoa.source.domain.page.dto.response.PageDashboardMemberDto;
import com.linkmoa.source.global.dto.request.BaseRequest;

import lombok.Builder;

public class SharePageDashboardDto {
	public record Request(
		BaseRequest baseRequest
	) {

	}

	@Builder
	public record Response(
		Long pageId,
		PageVisibility visibility,

		PageType pageType,
		List<PageDashboardMemberDto> pageMembers
	) {

	}
}
