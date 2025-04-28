package com.linkmoa.source.domain.member.repository;

import java.util.Optional;

import com.linkmoa.source.domain.member.entity.Member;

public interface MemberDataAccess {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	Member save(Member member);

	void delete(Member member);

	Optional<Member> findById(Long id);

}
