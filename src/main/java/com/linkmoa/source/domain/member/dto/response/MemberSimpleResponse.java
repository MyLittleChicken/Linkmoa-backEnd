package com.linkmoa.source.domain.member.dto.response;

public record MemberSimpleResponse(

	Long memberId,
	String email,
	String nickName,
	String colorCode

) {
}
