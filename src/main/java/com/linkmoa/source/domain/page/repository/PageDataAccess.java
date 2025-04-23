package com.linkmoa.source.domain.page.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.page.dto.response.PageDashboardMemberDto;
import com.linkmoa.source.domain.page.dto.response.PageResponse;
import com.linkmoa.source.domain.page.entity.Page;

public interface PageDataAccess {

	Page save(Page page);

	Optional<Page> findById(Long id);

	void deleteById(Long id);

	void delete(Page page);

	List<Object[]> findDirectoriesAndSitesByNameKeyword(String name, Long rootDirectoryId, Long memberId);

	List<PageResponse> findAllPagesByMemberId(Long memberId);

	Long findRootDirectoryIdByPageId(Long pageId);

	String findPageTitleById(Long pageId);

	List<PageDashboardMemberDto> findDashboardMembersByPageId(Long pageId);

	List<PageDashboardMemberDto> findWaitingInvitedMembersByPageId(Long pageId);

}
