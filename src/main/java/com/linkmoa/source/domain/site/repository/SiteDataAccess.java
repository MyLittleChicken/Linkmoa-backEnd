package com.linkmoa.source.domain.site.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.directory.constant.SortType;
import com.linkmoa.source.domain.site.dto.response.SiteDetailResponse;
import com.linkmoa.source.domain.site.dto.response.SiteSimpleResponse;
import com.linkmoa.source.domain.site.entity.Site;

public interface SiteDataAccess {
	Site save(Site site);

	Optional<Site> findById(Long id);

	void delete(Site site);

	List<SiteDetailResponse> findSitesDetails(Long directoryId, List<Long> favoriteSiteIds, SortType sortType);

	List<SiteSimpleResponse> findFavoriteSites(List<Long> favoriteSitesIds);
}
