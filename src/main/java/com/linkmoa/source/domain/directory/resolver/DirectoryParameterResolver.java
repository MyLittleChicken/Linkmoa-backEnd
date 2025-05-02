package com.linkmoa.source.domain.directory.resolver;

import org.springframework.stereotype.Component;

import com.linkmoa.source.domain.directory.constant.SortType;
import com.linkmoa.source.domain.directory.dto.request.DirectoryIdDto;
import com.linkmoa.source.global.constant.CommandType;
import com.linkmoa.source.global.dto.request.BaseRequest;

@Component
public class DirectoryParameterResolver {

	public BaseRequest createBaseRequestFrom(Long pageId, CommandType commandType) {
		return new BaseRequest(pageId, commandType);
	}

	public DirectoryIdDto.Request createDirectoryDetailsRequest(Long pageId, CommandType commandType, Long directroyId,
		SortType sortType) {
		return new DirectoryIdDto.Request(createBaseRequestFrom(pageId, commandType), directroyId, sortType);
	}

}
