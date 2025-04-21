package com.linkmoa.source.domain.site.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class SiteDetailResponse extends SiteSimpleResponse {
	private Integer orderIndex;
}
