package com.linkmoa.source.domain.directory.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.directory.dto.response.DirectoryDetailResponse;
import com.linkmoa.source.domain.directory.dto.response.DirectorySimpleResponse;
import com.linkmoa.source.domain.directory.entity.Directory;
import com.linkmoa.source.domain.directory.repository.DirectoryDataAccess;
import com.linkmoa.source.domain.directory.repository.rdb.DirectoryJpaRepository;
import com.linkmoa.source.domain.directory.repository.rdb.DirectoryQueryDslRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DirectoryDataAccessImpl implements DirectoryDataAccess {

	private final DirectoryJpaRepository directoryJpaRepository;
	private final DirectoryQueryDslRepositoryImpl directoryQueryDslRepository;

	@Override
	public Directory save(Directory directory) {
		return directoryJpaRepository.save(directory);
	}

	@Override
	public Optional<Directory> findById(Long id) {
		return directoryJpaRepository.findById(id);
	}

	@Override
	public void delete(Directory directory) {
		directoryJpaRepository.delete(directory);
	}

	@Override
	public List<DirectoryDetailResponse> findDirectoryDetails(Long directoryId, List<Long> favoriteDirectoryIds) {
		return directoryQueryDslRepository.findDirectoryDetails(directoryId, favoriteDirectoryIds);
	}

	@Override
	public void decrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.decrementDirectoryOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void decrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.decrementSiteOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void decrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.decrementDirectoryAndSiteOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void incrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.incrementDirectoryOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void incrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.incrementSiteOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void incrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex) {
		directoryQueryDslRepository.incrementDirectoryAndSiteOrderIndexes(parentDirectory, orderIndex);
	}

	@Override
	public void updateDirectoryOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue) {
		directoryQueryDslRepository.updateDirectoryOrderIndexesInRange(parentDirectory, startIndex, endIndex,
			adjustmentValue);
	}

	@Override
	public void updateSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue) {
		directoryQueryDslRepository.updateSiteOrderIndexesInRange(parentDirectory, startIndex, endIndex,
			adjustmentValue);
	}

	@Override
	public void updateDirectoryAndSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex,
		Integer endIndex, boolean isIncrement) {
		directoryQueryDslRepository.updateDirectoryAndSiteOrderIndexesInRange(parentDirectory, startIndex, endIndex,
			isIncrement);
	}

	@Override
	public List<DirectorySimpleResponse> findFavoriteDirectories(List<Long> favoriteDirectoryIds) {
		return directoryQueryDslRepository.findFavoriteDirectories(favoriteDirectoryIds);
	}

	@Override
	public String findFullPathByDirectoryId(Long directoryId) {
		return directoryJpaRepository.findFullPathByDirectoryId(directoryId);
	}

}
