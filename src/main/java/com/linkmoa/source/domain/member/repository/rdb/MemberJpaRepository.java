package com.linkmoa.source.domain.member.repository.rdb;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.member.entity.Member;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);
}
