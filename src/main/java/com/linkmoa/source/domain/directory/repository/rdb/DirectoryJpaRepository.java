package com.linkmoa.source.domain.directory.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linkmoa.source.domain.directory.entity.Directory;

public interface DirectoryJpaRepository extends JpaRepository<Directory, Long> {

	@Query(
		value = """
			WITH RECURSIVE directory_path AS (
			  SELECT d.directory_id, d.parent_directory_id, d.name, CAST(d.name AS CHAR) AS path
			  FROM directory d
			  WHERE d.directory_id = :directoryId
			  UNION ALL
			  SELECT parent.directory_id, parent.parent_directory_id, parent.name,
			         CONCAT(parent.name, '/', dp.path)
			  FROM directory parent
			  JOIN directory_path dp ON dp.parent_directory_id = parent.directory_id
			)
			SELECT path FROM directory_path ORDER BY LENGTH(path) DESC LIMIT 1
			""",
		nativeQuery = true
	)
	String findFullPathByDirectoryId(@Param("directoryId") Long directoryId);

}
