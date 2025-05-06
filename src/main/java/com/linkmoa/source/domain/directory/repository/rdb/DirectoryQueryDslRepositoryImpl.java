package com.linkmoa.source.domain.directory.repository.rdb;

import static com.linkmoa.source.domain.directory.entity.QDirectory.*;
import static com.linkmoa.source.domain.favorite.entity.QFavorite.*;
import static com.linkmoa.source.domain.site.entity.QSite.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.directory.constant.SortType;
import com.linkmoa.source.domain.directory.dto.response.DirectoryDetailResponse;
import com.linkmoa.source.domain.directory.dto.response.DirectorySimpleResponse;
import com.linkmoa.source.domain.directory.entity.Directory;
import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DirectoryQueryDslRepositoryImpl {
	private final JPAQueryFactory jpaQueryFactory;

	public List<DirectoryDetailResponse> findDirectoryDetails(Long directoryId, List<Long> favoriteDirectoryIds,
		SortType sortType) {

		OrderSpecifier<?> orderSpecifier = switch (sortType) {
			case NAME -> directory.directoryName.asc();
			case DATE -> directory.createdAt.desc();
			case BASIC -> directory.orderIndex.asc();
		};

		return jpaQueryFactory
			.selectFrom(directory)
			.where(directory.parentDirectory.id.eq(directoryId))
			.orderBy(orderSpecifier)
			.fetch()
			.stream()
			.map(d -> DirectoryDetailResponse.builder()
				.directoryId(d.getId())
				.directoryName(d.getDirectoryName())
				.orderIndex(sortType == SortType.BASIC ? d.getOrderIndex() : null)
				.isFavorite(favoriteDirectoryIds.contains(d.getId()))
				.build())
			.collect(Collectors.toList());

	}

	public void decrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		jpaQueryFactory.update(directory)
			.set(directory.orderIndex, directory.orderIndex.subtract(1))
			.where(directory.parentDirectory.eq(parentDirectory)
				.and(directory.orderIndex.gt(orderIndex)))
			.execute();
	}

	public void decrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		jpaQueryFactory.update(site)
			.set(site.orderIndex, site.orderIndex.subtract(1))
			.where(site.directory.eq(parentDirectory)
				.and(site.orderIndex.gt(orderIndex)))
			.execute();
	}

	public void decrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		decrementDirectoryOrderIndexes(parentDirectory, orderIndex);
		decrementSiteOrderIndexes(parentDirectory, orderIndex);
	}

	public void incrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		jpaQueryFactory.update(directory)
			.set(directory.orderIndex, directory.orderIndex.add(1))
			.where(directory.parentDirectory.eq(parentDirectory)
				.and(directory.orderIndex.gt(orderIndex)))
			.execute();
	}

	public void incrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		jpaQueryFactory.update(site)
			.set(site.orderIndex, site.orderIndex.add(1))
			.where(site.directory.eq(parentDirectory)
				.and(site.orderIndex.gt(orderIndex)))
			.execute();
	}

	public void incrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		incrementDirectoryOrderIndexes(parentDirectory, orderIndex);
		incrementSiteOrderIndexes(parentDirectory, orderIndex);
	}

	public void updateDirectoryOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue) {
		jpaQueryFactory.update(directory)
			.set(directory.orderIndex, directory.orderIndex.add(adjustmentValue))
			.where(directory.parentDirectory.eq(parentDirectory)
				.and(directory.orderIndex.between(startIndex, endIndex)))
			.execute();
	}

	public void updateSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue) {
		jpaQueryFactory.update(site)
			.set(site.orderIndex, site.orderIndex.add(adjustmentValue))
			.where(site.directory.eq(parentDirectory)
				.and(site.orderIndex.between(startIndex, endIndex)))
			.execute();
	}

	public void updateDirectoryAndSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex,
		Integer endIndex, boolean isIncrement) {
		Integer adjustmentValue = isIncrement ? 1 : -1;

		updateDirectoryOrderIndexesInRange(parentDirectory, startIndex, endIndex, adjustmentValue);
		updateSiteOrderIndexesInRange(parentDirectory, startIndex, endIndex, adjustmentValue);
	}

	public List<DirectorySimpleResponse> findFavoriteDirectories(List<Long> favoriteDirectoryIds) {
		if (favoriteDirectoryIds == null || favoriteDirectoryIds.isEmpty()) {
			return Collections.emptyList();
		}
		return jpaQueryFactory
			.select(
				Projections.constructor(
					DirectorySimpleResponse.class,
					directory.id,
					directory.directoryName,
					Expressions.constant(true) // isFavorite 값을 항상 true로 설정
				)
			)
			.from(directory)
			.join(favorite).on(directory.id.eq(favorite.itemId)
				.and(favorite.itemType.eq(ItemType.DIRECTORY)))
			.where(favorite.itemId.in(favoriteDirectoryIds))
			.orderBy(favorite.createdAt.asc())
			.fetch();
	}

	public List<DirectorySimpleResponse> findDirectoriesByKeywordAndPageId(
		final String keyword,
		final Long pageId,
		final Long memberId) {

		return jpaQueryFactory
			.select(
				Projections.constructor(
					DirectorySimpleResponse.class,
					directory.id,
					directory.directoryName,
					favorite.id.isNotNull()
				)
			)
			.from(directory)
			.leftJoin(favorite)
			.on(favorite.itemId.eq(directory.id)
				.and(favorite.itemType.eq(ItemType.DIRECTORY))
				.and(favorite.member.id.eq(memberId)))
			.where(
				directory.pageId.eq(pageId),
				directory.directoryName.containsIgnoreCase(keyword)
			)
			.fetch();
	}

}
