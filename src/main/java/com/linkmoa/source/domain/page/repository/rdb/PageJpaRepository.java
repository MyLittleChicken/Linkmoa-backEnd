package com.linkmoa.source.domain.page.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.linkmoa.source.domain.page.entity.Page;

public interface PageJpaRepository extends JpaRepository<Page, Long> {

	@Query("SELECT p.pageTitle FROM Page p WHERE p.id = :pageId")
	String findPageTitleById(@Param("pageId") Long pageId);
}
