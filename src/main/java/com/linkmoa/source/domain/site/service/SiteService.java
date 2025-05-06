package com.linkmoa.source.domain.site.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;
import com.linkmoa.source.domain.directory.entity.Directory;
import com.linkmoa.source.domain.directory.error.DirectoryErrorCode;
import com.linkmoa.source.domain.directory.exception.DirectoryException;
import com.linkmoa.source.domain.directory.repository.DirectoryDataAccess;
import com.linkmoa.source.domain.site.dto.request.SiteCreateDto;
import com.linkmoa.source.domain.site.dto.request.SiteDeleteDto;
import com.linkmoa.source.domain.site.dto.request.SiteMoveRequestDto;
import com.linkmoa.source.domain.site.dto.request.SiteUpdateRequestDto;
import com.linkmoa.source.domain.site.entity.Site;
import com.linkmoa.source.domain.site.error.SiteErrorCode;
import com.linkmoa.source.domain.site.exception.SiteException;
import com.linkmoa.source.domain.site.repository.SiteDataAccess;
import com.linkmoa.source.global.aop.annotation.ValidationApplied;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class SiteService {

	private final SiteDataAccess siteDataAccess;
	private final DirectoryDataAccess directoryDataAccess;

	@ValidationApplied
	public Long createSite(SiteCreateDto.Request request,
		PrincipalDetails principalDetails) {

		Directory directory = directoryDataAccess.findById(request.directoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		Integer nextOrderIndex = directory.getNextOrderIndex();

		Site newSite = Site.builder()
			.siteName(request.siteName())
			.siteUrl(request.siteUrl())
			.directory(directory)
			.orderIndex(nextOrderIndex)
			.build();

		siteDataAccess.save(newSite);
		return newSite.getId();

	}

	private static String extractFaviconUrl(String siteUrl) {
		try {
			URL url = new URL(siteUrl);
			return url.getProtocol() + "://" + url.getHost() + "/favicon.ico";
		} catch (MalformedURLException e) {
			throw new SiteException(SiteErrorCode.INVALID_URL);
		}
	}

	@ValidationApplied
	public Long updateSite(SiteUpdateRequestDto request,
		PrincipalDetails principalDetails) {

		Site updateSite = siteDataAccess.findById(request.siteId())
			.orElseThrow(() -> new SiteException(SiteErrorCode.SITE_NOT_FOUND));

		updateSite.updateSiteNameAndUrl(request.siteName(), request.siteUrl());

		return updateSite.getId();

	}

	@ValidationApplied
	public Long deleteSite(SiteDeleteDto.Request request,
		PrincipalDetails principalDetails) {

		Site deleteSite = siteDataAccess.findById(request.siteId())
			.orElseThrow(() -> new SiteException(SiteErrorCode.SITE_NOT_FOUND));

		Directory parentDirectory = deleteSite.getDirectory();
		Integer orderIndex = deleteSite.getOrderIndex();

		directoryDataAccess.decrementDirectoryAndSiteOrderIndexes(parentDirectory, orderIndex);
		siteDataAccess.delete(deleteSite);

		return deleteSite.getId();
	}

	@ValidationApplied
	public Long moveSite(SiteMoveRequestDto request, PrincipalDetails principalDetails) {

		Site moveSite = siteDataAccess.findById(request.siteId())
			.orElseThrow(() -> new SiteException(SiteErrorCode.SITE_NOT_FOUND));

		Directory targetDirectory = directoryDataAccess.findById(request.targetDirectoryId())
			.orElseThrow(() -> new DirectoryException(DirectoryErrorCode.DIRECTORY_NOT_FOUND));

		directoryDataAccess.decrementDirectoryAndSiteOrderIndexes(moveSite.getDirectory(), moveSite.getOrderIndex());

		Integer newOrderIndex = targetDirectory.getNextOrderIndex();

		moveSite.setDirectory(targetDirectory);

		moveSite.setOrderIndex(newOrderIndex);

		return moveSite.getId();

	}

}
