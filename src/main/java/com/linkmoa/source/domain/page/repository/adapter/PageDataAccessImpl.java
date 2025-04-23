package com.linkmoa.source.domain.page.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.page.contant.PageVisibility;
import com.linkmoa.source.domain.page.dto.response.PageDashboardMemberDto;
import com.linkmoa.source.domain.page.dto.response.PageResponse;
import com.linkmoa.source.domain.page.entity.Page;
import com.linkmoa.source.domain.page.repository.PageDataAccess;
import com.linkmoa.source.domain.page.repository.rdb.PageJpaRepository;
import com.linkmoa.source.domain.page.repository.rdb.PageQueryDslRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PageDataAccessImpl implements PageDataAccess {

	private final PageJpaRepository pageJpaRepository;
	private final PageQueryDslRepositoryImpl pageQueryDslRepository;

	@Override
	public Page save(Page page) {
		return pageJpaRepository.save(page);
	}

	@Override
	public Optional<Page> findById(Long id) {
		return pageJpaRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		pageJpaRepository.deleteById(id);
	}

	@Override
	public void delete(Page page) {
		pageJpaRepository.delete(page);
	}

	@Override
	public List<Object[]> findDirectoriesAndSitesByNameKeyword(String name, Long rootDirectoryId, Long memberId) {
		return pageJpaRepository.findDirectoriesAndSitesByNameKeyword(name, rootDirectoryId, memberId);
	}

	@Override
	public List<PageResponse> findAllPagesByMemberId(Long memberId) {
		return pageQueryDslRepository.findAllPagesByMemberId(memberId);
	}

	@Override
	public Long findRootDirectoryIdByPageId(Long pageId) {
		return pageQueryDslRepository.findRootDirectoryIdByPageId(pageId);
	}

	@Override
	public String findPageTitleById(Long pageId) {
		return pageJpaRepository.findPageTitleById(pageId);
	}

	@Override
	public List<PageDashboardMemberDto> findDashboardMembersByPageId(Long pageId) {
		return pageQueryDslRepository.findDashboardMembersByPageId(pageId);
	}

	@Override
	public List<PageDashboardMemberDto> findWaitingInvitedMembersByPageId(Long pageId) {
		return pageQueryDslRepository.findWaitingInvitedMembersByPageId(pageId);
	}

	@Override
	public PageVisibility findPageVisibilityByPageId(Long pageId) {
		return pageQueryDslRepository.findPageVisibilityByPageId(pageId);
	}
}
