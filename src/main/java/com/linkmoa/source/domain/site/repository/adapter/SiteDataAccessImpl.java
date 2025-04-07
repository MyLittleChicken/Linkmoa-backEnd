package com.linkmoa.source.domain.site.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.site.dto.response.SiteDetailResponse;
import com.linkmoa.source.domain.site.dto.response.SiteSimpleResponse;
import com.linkmoa.source.domain.site.entity.Site;
import com.linkmoa.source.domain.site.repository.SiteDataAccess;
import com.linkmoa.source.domain.site.repository.rdb.SiteJpaRepository;
import com.linkmoa.source.domain.site.repository.rdb.SiteQueryDslRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SiteDataAccessImpl implements SiteDataAccess {

	private final SiteJpaRepository siteJpaRepository;
	private final SiteQueryDslRepositoryImpl siteQueryDslRepository;

	@Override
	public Site save(Site site) {
		return siteJpaRepository.save(site);
	}

	@Override
	public Optional<Site> findById(Long id) {
		return siteJpaRepository.findById(id);
	}

	@Override
	public void delete(Site site) {
		siteJpaRepository.delete(site);
	}

	@Override
	public List<SiteDetailResponse> findSitesDetails(Long directoryId, List<Long> favoriteSiteIds) {
		return siteQueryDslRepository.findSitesDetails(directoryId, favoriteSiteIds);
	}

	@Override
	public List<SiteSimpleResponse> findFavoriteSites(List<Long> favoriteSitesIds) {
		return siteQueryDslRepository.findFavoriteSites(favoriteSitesIds);
	}
}
