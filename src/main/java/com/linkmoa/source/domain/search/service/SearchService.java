package com.linkmoa.source.domain.search.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;
import com.linkmoa.source.domain.directory.dto.response.DirectorySimpleResponse;
import com.linkmoa.source.domain.directory.repository.DirectoryDataAccess;
import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.linkmoa.source.domain.member.repository.MemberDataAccess;
import com.linkmoa.source.domain.search.dto.request.MemberSearchRequestDto;
import com.linkmoa.source.domain.search.dto.request.SearchRequest;
import com.linkmoa.source.domain.search.dto.response.SearchPageResponse;
import com.linkmoa.source.domain.site.dto.response.SiteSimpleResponse;
import com.linkmoa.source.domain.site.repository.SiteDataAccess;
import com.linkmoa.source.global.spec.ApiResponseSpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

	private final MemberDataAccess memberDataAccess;
	private final DirectoryDataAccess directoryDataAccess;
	private final SiteDataAccess siteDataAccess;

	public ApiResponseSpec<SearchPageResponse> searchDirectoriesAndSitesByTitleInPage(
		final SearchRequest request,
		final PrincipalDetails principalDetails) {

		List<DirectorySimpleResponse> directories = directoryDataAccess.findDirectoriesByKeywordAndPageId(
			request.keyword(),
			request.pageId(),
			principalDetails.getMember().getId());

		List<SiteSimpleResponse> sites = siteDataAccess.findSitesByKeywordAndPageId(
			request.keyword(),
			request.pageId(),
			principalDetails.getMember().getId());

		SearchPageResponse searchPageResponse = SearchPageResponse.builder()
			.directorySimpleResponses(directories)
			.siteSimpleResponses(sites)
			.build();

		return ApiResponseSpec.success(
			HttpStatus.OK,
			"사이트 내에 있는 모든 디렉토리와 사이트를 제목으로 검색합니다.",
			searchPageResponse
		);
	}

	public MemberSearchRequestDto.Response searchMembersByEmailOrNickname(
		String keyword) {
		List<MemberSimpleResponse> memberSimpleResponses = memberDataAccess.searchByKeyword(keyword);

		return new MemberSearchRequestDto.Response(memberSimpleResponses);

	}

}
