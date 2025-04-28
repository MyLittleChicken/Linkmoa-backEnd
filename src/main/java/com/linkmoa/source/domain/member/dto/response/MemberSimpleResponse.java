package com.linkmoa.source.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSimpleResponse(

	Long memberId,
	String email,
	String nickName

) {
}
