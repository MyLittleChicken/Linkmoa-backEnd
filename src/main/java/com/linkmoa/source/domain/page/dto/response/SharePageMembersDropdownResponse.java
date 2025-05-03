package com.linkmoa.source.domain.page.dto.response;

import java.util.List;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;

public record SharePageMembersDropdownResponse(
	List<MemberSimpleResponse> members
) {
}
