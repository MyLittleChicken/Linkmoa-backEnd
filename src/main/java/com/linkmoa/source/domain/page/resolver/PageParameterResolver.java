package com.linkmoa.source.domain.page.resolver;

import org.springframework.stereotype.Component;

import com.linkmoa.source.domain.page.dto.request.SharePageDashboardDto;
import com.linkmoa.source.domain.page.dto.request.SharePageMembersDropdownDto;
import com.linkmoa.source.global.constant.CommandType;
import com.linkmoa.source.global.dto.request.BaseRequest;

@Component
public class PageParameterResolver {

	/*
	createFrom(...)	명시적이며 팩토리 느낌
 	resolveFrom(...)	"입력으로부터 객체를 만들어낸다"는 의미 + 리졸버 컨텍스트에 잘 어울림
	buildFrom(...)	Builder 느낌, 복잡한 조합일 때
	convert(...)	변환 느낌, DTO → Entity처럼 매핑할 때 자주 사용
	 */
	public BaseRequest createBaseRequestFrom(Long pageId, CommandType commandType) {
		return new BaseRequest(pageId, commandType);
	}

	public SharePageDashboardDto.Request createDashboardRequest(Long pageId, CommandType commandType) {
		return new SharePageDashboardDto.Request(createBaseRequestFrom(pageId, commandType));
	}

	public SharePageMembersDropdownDto.Request createSharePageMembersRequest(Long pageId, CommandType commandType) {
		return new SharePageMembersDropdownDto.Request(createBaseRequestFrom(pageId, commandType));
	}

}
