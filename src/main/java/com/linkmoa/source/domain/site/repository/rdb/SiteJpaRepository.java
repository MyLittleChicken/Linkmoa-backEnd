package com.linkmoa.source.domain.site.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.site.entity.Site;

public interface SiteJpaRepository extends JpaRepository<Site, Long> {
}
