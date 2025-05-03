package com.linkmoa.source.domain.member.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.member.dto.response.MemberSimpleResponse;
import com.linkmoa.source.domain.member.entity.Member;

public interface MemberDataAccess {

	Optional<Member> findByEmail(String email);

	boolean existsByEmail(String email);

	Member save(Member member);

	void delete(Member member);

	Optional<Member> findById(Long id);

	List<MemberSimpleResponse> searchByKeyword(String keyword);

	List<MemberSimpleResponse> findMembersBySharePageId(Long pageId);

}
