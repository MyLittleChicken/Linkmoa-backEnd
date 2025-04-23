package com.linkmoa.source.domain.page.contant;

import lombok.Getter;

@Getter
public enum PageVisibility {

	PUBLIC("링크를 가진 누구나 접근 가능"),
	RESTRICTED("참여 멤버(viewer 이상)만 접근 가능 ");

	private final String description;

	PageVisibility(String description) {
		this.description = description;
	}
}
