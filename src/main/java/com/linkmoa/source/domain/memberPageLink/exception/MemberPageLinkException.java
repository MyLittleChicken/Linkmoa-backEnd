package com.linkmoa.source.domain.memberPageLink.exception;

import com.linkmoa.source.domain.memberPageLink.error.MemberPageLinkErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberPageLinkException extends RuntimeException {
	private final MemberPageLinkErrorCode memberPageLinkErrorCode;
}
