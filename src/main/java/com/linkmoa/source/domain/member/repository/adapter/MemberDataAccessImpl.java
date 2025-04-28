package com.linkmoa.source.domain.member.repository.adapter;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.entity.Member;
import com.linkmoa.source.domain.member.repository.MemberDataAccess;
import com.linkmoa.source.domain.member.repository.rdb.MemberJpaRepository;
import com.linkmoa.source.domain.member.repository.rdb.MemberQueryDslRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberDataAccessImpl implements MemberDataAccess {

	private final MemberJpaRepository memberJpaRepository;
	private final MemberQueryDslRepository memberQueryDslRepository;

	@Override
	public Optional<Member> findByEmail(String email) {
		return memberJpaRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return memberJpaRepository.existsByEmail(email);
	}

	@Override
	public Member save(Member member) {
		return memberJpaRepository.save(member);
	}

	@Override
	public void delete(Member member) {
		memberJpaRepository.delete(member);
	}

	@Override
	public Optional<Member> findById(Long id) {
		return memberJpaRepository.findById(id);
	}
}
