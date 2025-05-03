package com.linkmoa.source.domain.page.dto.request;

import java.util.List;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.linkmoa.source.global.dto.request.BaseRequest;

public class SharePageMembersDropdownDto {

	public record Request(
		BaseRequest baseRequest
	) {

	}

	public record Response(
		List<MemberSimpleResponse> members
	) {

	}
}
