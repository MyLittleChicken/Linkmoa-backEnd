package com.linkmoa.source.domain.directory.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linkmoa.source.domain.directory.entity.Directory;

public interface DirectoryJpaRepository extends JpaRepository<Directory, Long> {

	@Query(
		value = """
			  WITH RECURSIVE directory_path AS (
			    SELECT id, parent_directory_id, name, CAST(name AS CHAR) AS path
			    FROM directory
			    WHERE id = :currentDirectoryId
			    UNION ALL
			    SELECT d.id, d.parent_directory_id, d.name, CONCAT(d.name, '/', dp.path)
			    FROM directory d
			    JOIN directory_path dp ON dp.parent_directory_id = d.id
			  )
			  SELECT path FROM directory_path ORDER BY LENGTH(path) DESC LIMIT 1;
			""",
		nativeQuery = true
	)
	String findFullPathByDirectoryId(@Param("currentDirectoryId") Long directoryId);

}
