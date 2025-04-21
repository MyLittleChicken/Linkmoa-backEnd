package com.linkmoa.source.domain.directory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;
import com.linkmoa.source.domain.directory.dto.request.DirectoryChangeParentDto;
import com.linkmoa.source.domain.directory.dto.request.DirectoryCreateDto;
import com.linkmoa.source.domain.directory.dto.request.DirectoryDragAndDropDto;
import com.linkmoa.source.domain.directory.dto.request.DirectoryIdDto;
import com.linkmoa.source.domain.directory.dto.request.DirectoryPasteDto;
import com.linkmoa.source.domain.directory.dto.request.DirectoryUpdateDto;
import com.linkmoa.source.domain.directory.dto.response.DirectoryDetailResponse;
import com.linkmoa.source.domain.directory.entity.Directory;
import com.linkmoa.source.domain.directory.error.DirectoryErrorCode;
import com.linkmoa.source.domain.directory.exception.DirectoryException;
import com.linkmoa.source.domain.directory.repository.DirectoryDataAccess;
import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.linkmoa.source.domain.favorite.entity.Favorite;
import com.linkmoa.source.domain.favorite.repository.FavoriteDataAccess;
import com.linkmoa.source.domain.favorite.service.FavoriteService;
import com.linkmoa.source.domain.page.repository.PageDataAccess;
import com.linkmoa.source.domain.site.dto.response.SiteDetailResponse;
import com.linkmoa.source.domain.site.entity.Site;
import com.linkmoa.source.domain.site.error.SiteErrorCode;
import com.linkmoa.source.domain.site.exception.SiteException;
import com.linkmoa.source.domain.site.repository.SiteDataAccess;
import com.linkmoa.source.global.aop.annotation.ValidationApplied;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DirectoryService {

	private final DirectoryDataAccess directoryDataAccess;
	private final SiteDataAccess siteDataAccess;
	private final FavoriteDataAccess favoriteDataAccess;
	private final FavoriteService favoriteService;
	private final PageDataAccess pageDataAccess;

	@Transactional
	@ValidationApplied
	public Long createDirectory(DirectoryCreateDto.Request request,
		PrincipalDetails principalDetails) {

		Directory parentDirectory = null;
		Integer nextOrderIndex;

		if (request.parentDirectoryId() != null) {
			parentDirectory = directoryDataAccess.findById(request.parentDirectoryId())
				.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
			nextOrderIndex = parentDirectory.getNextOrderIndex();
		} else {
			nextOrderIndex = 1; // 또는 적절한 기본값
		}

		Directory newDirectory = Directory.builder()
			.directoryName(request.directoryName())
			.directoryDescription(request.directoryDescription())
			.orderIndex(nextOrderIndex)
			.build();

		// 부모 디렉토리에 새 디렉토리 추가
		if (parentDirectory != null) {
			parentDirectory.addChildDirectory(newDirectory);
		}

		directoryDataAccess.save(newDirectory);

		return newDirectory.getId();

	}

	@Transactional
	@ValidationApplied
	public Long updateDirectory(DirectoryUpdateDto.Request request,
		PrincipalDetails principalDetails) {

		Directory updateDirectory = directoryDataAccess.findById(request.directoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		updateDirectory.updateDirectoryNameAndDescription(request.directoryName(),
			request.directoryDescription());

		return updateDirectory.getId();
	}

	@Transactional
	@ValidationApplied
	public Long deleteDirectory(DirectoryIdDto.Request request,
		PrincipalDetails principalDetails) {

		Directory deleteDirectory = directoryDataAccess.findById(request.directoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		Long directoryId = deleteDirectory.getId();
		Integer orderIndex = deleteDirectory.getOrderIndex();
		Directory parentDirectory = deleteDirectory.getParentDirectory();

		directoryDataAccess.decrementDirectoryAndSiteOrderIndexes(parentDirectory, orderIndex);
		directoryDataAccess.delete(deleteDirectory);

		return directoryId;
	}

	@Transactional
	@ValidationApplied
	public Long changeParentDirectory(DirectoryChangeParentDto.Request request,
		PrincipalDetails principalDetails) {

		Directory movingDirectory = directoryDataAccess.findById(request.movingDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		Directory newParentDirectory = directoryDataAccess.findById(request.newParentDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		directoryDataAccess.decrementDirectoryAndSiteOrderIndexes(
			movingDirectory.getParentDirectory(),
			movingDirectory.getOrderIndex()
		);

		movingDirectory.setParentDirectory(newParentDirectory);
		movingDirectory.setOrderIndex(newParentDirectory.getNextOrderIndex());

		return movingDirectory.getId();
	}

	@Transactional
	@ValidationApplied
	public DirectoryDragAndDropDto.Response dragAndDropDirectoryOrSite(
		DirectoryDragAndDropDto.Request request,
		PrincipalDetails principalDetails) {

		Integer currentItemOrderIndex;

		Directory parentDirectory = directoryDataAccess.findById(request.parentDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		currentItemOrderIndex = getOrderIndex(
			request.targetId(),
			request.itemType()
		);

		boolean isIncrement = currentItemOrderIndex > request.targetOrderIndex();
		int startIndex = Math.min(currentItemOrderIndex, request.targetOrderIndex());
		int endIndex = Math.max(currentItemOrderIndex, request.targetOrderIndex());

		directoryDataAccess.updateDirectoryAndSiteOrderIndexesInRange(parentDirectory, startIndex, endIndex,
			isIncrement);

		setOrderIndex(
			request.targetId(),
			request.itemType(),
			request.targetOrderIndex()
		);

		return DirectoryDragAndDropDto.Response.builder()
			.targetId(request.targetId())
			.itemType(String.valueOf(request.itemType()))
			.targetOrderIndex(request.targetOrderIndex())
			.build();
	}

	private Integer getOrderIndex(Long targetId, ItemType itemType) {
		switch (itemType) {
			case DIRECTORY -> {
				Directory directory = directoryDataAccess.findById(targetId)
					.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
				return directory.getOrderIndex();
			}
			case SITE -> {
				Site site = siteDataAccess.findById(targetId)
					.orElseThrow(() -> new SiteException(SiteErrorCode.SITE_NOT_FOUND));
				return site.getOrderIndex();
			}
			default -> throw new DirectoryException(DirectoryErrorCode.UNSUPPORTED_TARGET_TYPE);
		}
	}

	private void setOrderIndex(Long targetId, ItemType itemType,
		Integer targetOrderIndex) {
		switch (itemType) {
			case DIRECTORY -> {
				Directory directory = directoryDataAccess.findById(targetId)
					.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));
				directory.setOrderIndex(targetOrderIndex);
			}
			case SITE -> {
				Site site = siteDataAccess.findById(targetId)
					.orElseThrow(() -> new SiteException(SiteErrorCode.SITE_NOT_FOUND));
				site.setOrderIndex(targetOrderIndex);
			}
			default -> throw new DirectoryException(DirectoryErrorCode.UNSUPPORTED_TARGET_TYPE);
		}
	}

	@ValidationApplied
	public DirectoryIdDto.Response findDirectoryDetails(
		DirectoryIdDto.Request request,
		PrincipalDetails principalDetails) {

		Directory targetDirectory = directoryDataAccess.findById(request.directoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		List<Favorite> favorites = favoriteDataAccess.findByMember(principalDetails.getMember());

		List<Long> favoriteDirectoryIds = favoriteService.findFavoriteDirectoryIds(favorites);
		List<Long> favoriteSiteIds = favoriteService.findFavoriteSiteIds(favorites);

		List<DirectoryDetailResponse> directoryDetailResponses =
			directoryDataAccess.findDirectoryDetails(targetDirectory.getId(), favoriteDirectoryIds);

		List<SiteDetailResponse> siteDetailResponses =
			siteDataAccess.findSitesDetails(targetDirectory.getId(), favoriteSiteIds);

		return DirectoryIdDto.Response.builder()
			.targetDirectoryDescription(targetDirectory.getDirectoryDescription())
			.targetDirectoryName(targetDirectory.getDirectoryName())
			.directoryDetailResponses(directoryDetailResponses)
			.siteDetailResponses(siteDetailResponses)
			.directoryFullPath(findFullPath(targetDirectory.getId(), request.baseRequest().pageId()))
			.build();
	}

	private String findFullPath(Long directoryId, Long pageId) {
		String directoryFullPath = directoryDataAccess.findFullPathByDirectoryId(directoryId);
		String pageTitle = pageDataAccess.findPageTitleById(pageId);
		return pageTitle + "/" + directoryFullPath;

	}

	@Transactional
	@ValidationApplied
	public DirectoryPasteDto.Response pasteDirectory(
		DirectoryPasteDto.Request request,
		PrincipalDetails principalDetails) {

		Directory originalDirectory = directoryDataAccess.findById(request.originalDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		Directory destinationDirectory = directoryDataAccess.findById(request.destinationDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		directoryDataAccess.incrementDirectoryAndSiteOrderIndexes(destinationDirectory, 0);

		Directory pastedDirectory = originalDirectory.cloneDirectory(destinationDirectory);
		pastedDirectory.setOrderIndex(1);
		directoryDataAccess.save(pastedDirectory);

		return DirectoryPasteDto.Response.builder()
			.pastedirectoryId(pastedDirectory.getId())
			.destinationDirectoryId(destinationDirectory.getId())
			.clonedDirectoryName(pastedDirectory.getDirectoryName())
			.build();
	}

	public Directory cloneDirectory(Long newRootDirectoryId, Long originalDirectoryId) {

		Directory originalDirectory = directoryDataAccess.findById(originalDirectoryId)
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		Directory newParentDirectory = directoryDataAccess.findById(newRootDirectoryId).orElse(null);

		directoryDataAccess.incrementDirectoryAndSiteOrderIndexes(newParentDirectory, 0);

		Directory clonedDirectory = originalDirectory.cloneDirectory(newParentDirectory);
		clonedDirectory.setOrderIndex(1);
		directoryDataAccess.save(clonedDirectory);
		return clonedDirectory;
	}
}
