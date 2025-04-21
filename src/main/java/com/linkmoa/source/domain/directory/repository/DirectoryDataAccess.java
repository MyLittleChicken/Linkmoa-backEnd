package com.linkmoa.source.domain.directory.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.directory.dto.response.DirectoryDetailResponse;
import com.linkmoa.source.domain.directory.dto.response.DirectorySimpleResponse;
import com.linkmoa.source.domain.directory.entity.Directory;

public interface DirectoryDataAccess {
	// ⭐ 추가해야 하는 기본 CRUD 메서드
	Directory save(Directory directory);

	Optional<Directory> findById(Long id);

	void delete(Directory directory);

	List<DirectoryDetailResponse> findDirectoryDetails(Long directoryId, List<Long> favoriteDirectoryIds);

	void decrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void decrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void decrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void incrementDirectoryOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void incrementSiteOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void incrementDirectoryAndSiteOrderIndexes(Directory parentDirectory, Integer orderIndex);

	void updateDirectoryOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue);

	void updateSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		Integer adjustmentValue);

	void updateDirectoryAndSiteOrderIndexesInRange(Directory parentDirectory, Integer startIndex, Integer endIndex,
		boolean isIncrement);

	List<DirectorySimpleResponse> findFavoriteDirectories(List<Long> favoriteDirectoryIds);

	String findFullPathByDirectoryId(Long directoryId);

}
