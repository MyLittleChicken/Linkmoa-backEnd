package com.linkmoa.source.domain.directory.repository.rdb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.directory.entity.Directory;

public interface DirectoryJpaRepository extends JpaRepository<Directory, Long> {
}
