package com.linkmoa.source.domain.site.repository.rdb;

import static com.linkmoa.source.domain.favorite.entity.QFavorite.*;
import static com.linkmoa.source.domain.site.entity.QSite.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.directory.constant.SortType;
import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.linkmoa.source.domain.site.dto.response.SiteDetailResponse;
import com.linkmoa.source.domain.site.dto.response.SiteSimpleResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class SiteQueryDslRepositoryImpl {
	private final JPAQueryFactory jpaQueryFactory;

	public List<SiteDetailResponse> findSitesDetails(Long directoryId, List<Long> favoriteSiteIds, SortType sortType) {

		OrderSpecifier<?> orderSpecifier = switch (sortType) {
			case NAME -> site.siteName.asc();
			case DATE -> site.createdAt.desc();
			case BASIC -> site.orderIndex.asc();
		};

		return jpaQueryFactory
			.selectFrom(site)
			.where(site.directory.id.eq(directoryId))
			.orderBy(orderSpecifier)
			.fetch()
			.stream()
			.map(s -> SiteDetailResponse.builder()
				.siteId(s.getId())
				.siteUrl(s.getSiteUrl())
				.siteName(s.getSiteName())
				.orderIndex(sortType == SortType.BASIC ? s.getOrderIndex() : null)
				.isFavorite(favoriteSiteIds.contains(s.getId()))
				.build())
			.collect(Collectors.toList());

	}

	public List<SiteSimpleResponse> findFavoriteSites(List<Long> favoriteSiteIds) {
		if (favoriteSiteIds == null || favoriteSiteIds.isEmpty()) {
			return Collections.emptyList();
		}
		return jpaQueryFactory
			.select(
				Projections.constructor(
					SiteSimpleResponse.class,
					site.id,
					site.siteName,
					site.siteUrl,
					Expressions.constant(true),
					site.faviconUrl
				)
			)
			.from(site)
			.join(favorite).on(site.id.eq(favorite.itemId)
				.and(favorite.itemType.eq(ItemType.SITE)))
			.where(favorite.itemId.in(favoriteSiteIds))
			.orderBy(favorite.createdAt.asc())
			.fetch();
	}
}
