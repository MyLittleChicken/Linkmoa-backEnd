package com.linkmoa.source.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;
import com.linkmoa.source.domain.directory.dto.response.DirectorySimpleResponse;
import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.linkmoa.source.domain.member.repository.MemberDataAccess;
import com.linkmoa.source.domain.page.repository.PageDataAccess;
import com.linkmoa.source.domain.search.dto.request.MemberSearchRequestDto;
import com.linkmoa.source.domain.search.dto.request.SearchRequest;
import com.linkmoa.source.domain.search.dto.response.SearchPageResponse;
import com.linkmoa.source.domain.site.dto.response.SiteSimpleResponse;
import com.linkmoa.source.global.spec.ApiResponseSpec;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

	private final PageDataAccess pageDataAccess;
	private final MemberDataAccess memberDataAccess;

	public ApiResponseSpec<SearchPageResponse> searchDirectoriesAndSitesByTitleInPage(SearchRequest searchRequest,
		PrincipalDetails principalDetails) {
		Long rootDirectoryIdByPageId = pageDataAccess.findRootDirectoryIdByPageId(searchRequest.pageId());

		log.debug("🔍 pageSerivce Search 요청: pageId={}, keyword='{}', type={}",
			searchRequest.pageId(),
			searchRequest.keyword(),
			searchRequest.searchType());

		List<Object[]> directoriesAndSitesByKeyword = pageDataAccess.findDirectoriesAndSitesByNameKeyword(
			searchRequest.keyword(),
			rootDirectoryIdByPageId,
			principalDetails.getId());
		log.debug("🔍 pageDataAccess Search 요청: ");

		List<DirectorySimpleResponse> directories = mapToDirectoryResponses(
			directoriesAndSitesByKeyword);

		List<SiteSimpleResponse> sites = mapToSiteResponses(directoriesAndSitesByKeyword);

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

	private List<DirectorySimpleResponse> mapToDirectoryResponses(List<Object[]> rawResults) {
		List<DirectorySimpleResponse> directories = new ArrayList<>();

		for (Object[] row : rawResults) {
			Long directoryId = (Long)row[0];
			String directoryName = (String)row[1];
			Boolean isDirectoryFavorite = (Boolean)row[2];

			directories.add(
				DirectorySimpleResponse.builder()
					.directoryId(directoryId)
					.directoryName(directoryName)
					.isFavorite(isDirectoryFavorite)
					.build()
			);
		}

		return directories;
	}

	private List<SiteSimpleResponse> mapToSiteResponses(List<Object[]> rawResults) {
		List<SiteSimpleResponse> sites = new ArrayList<>();

		for (Object[] row : rawResults) {
			Long siteId = (Long)row[3];
			String siteName = (String)row[4];
			String siteUrl = (String)row[5];
			Boolean isSiteFavorite = (Boolean)row[6];

			sites.add(
				SiteSimpleResponse.builder()
					.siteId(siteId)
					.siteName(siteName)
					.siteUrl(siteUrl)
					.isFavorite(isSiteFavorite)
					.build()
			);

		}
		return sites;
	}

	public MemberSearchRequestDto.Response searchMembersByEmailOrNickname(
		MemberSearchRequestDto.Request request) {
		List<MemberSimpleResponse> memberSimpleResponses = memberDataAccess.searchByKeyword(request.keyword());

		return new MemberSearchRequestDto.Response(memberSimpleResponses);

	}

}
