package com.linkmoa.source.domain.search.dto.request;

import java.util.List;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;

public class MemberSearchRequestDto {

	public record Request(
		String Keyword
	) {

	}

	public record Response(

		List<MemberSimpleResponse> members

	) {

	}
}
