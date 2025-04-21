package com.linkmoa.source.domain.directory.dto.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class DirectoryDetailResponse extends DirectorySimpleResponse {

	private Integer orderIndex;
}
