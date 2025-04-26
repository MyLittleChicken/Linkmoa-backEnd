package com.linkmoa.source.domain.memberPageLink.error;

import org.springframework.http.HttpStatus;

import com.linkmoa.source.global.error.code.spec.ErrorCode;

import lombok.Getter;

@Getter
public enum MemberPageLinkErrorCode implements ErrorCode {

	MEMBER_PAGE_LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "멤버와 페이지 간의 연결 정보를 찾을 수 없습니다.");

	private HttpStatus httpStatus;
	private String errorMessage;

	MemberPageLinkErrorCode(HttpStatus httpStatus, String errorMessage) {
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
}
