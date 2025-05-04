package com.linkmoa.source.auth.oauth2.dto.response;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.linkmoa.source.domain.page.dto.response.PageDetailsResponse;

public record LoginSuccessResponse(
	MemberSimpleResponse member,
	PageDetailsResponse pageDetails
) {
}
