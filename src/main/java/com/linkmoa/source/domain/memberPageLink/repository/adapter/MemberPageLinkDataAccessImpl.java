package com.linkmoa.source.domain.memberPageLink.repository.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.member.entity.Member;
import com.linkmoa.source.domain.memberPageLink.constant.PermissionType;
import com.linkmoa.source.domain.memberPageLink.entity.MemberPageLink;
import com.linkmoa.source.domain.memberPageLink.repository.MemberPageLinkDataAccess;
import com.linkmoa.source.domain.memberPageLink.repository.rdb.MemberPageLinkJpaRepository;
import com.linkmoa.source.domain.memberPageLink.repository.rdb.MemberPageLinkQueryRepositoryImpl;
import com.linkmoa.source.domain.page.entity.Page;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberPageLinkDataAccessImpl implements MemberPageLinkDataAccess {

	private final MemberPageLinkJpaRepository memberPageLinkJpaRepository;
	private final MemberPageLinkQueryRepositoryImpl memberPageLinkQueryRepository;

	@Override
	public Optional<Page> findPersonalPageByMemberId(Long memberId) {
		return memberPageLinkQueryRepository.findPersonalPageByMemberId(memberId);
	}

	@Override
	public PermissionType findPermissionTypeByMemberIdAndPageId(Long memberId, Long pageId) {
		return memberPageLinkJpaRepository.findPermissionTypeByMemberIdAndPageId(memberId, pageId);
	}

	@Override
	public void deleteByMemberIdAndPageId(Long memberId, Long pageId) {
		memberPageLinkJpaRepository.deleteByMemberIdAndPageId(memberId, pageId);
	}

	@Override
	public Optional<MemberPageLink> findByMemberAndPage(Long memberId, Long pageId) {
		return memberPageLinkJpaRepository.findByMemberAndPage(memberId, pageId);
	}

	@Override
	public boolean existsByMemberAndPage(Long memberId, Long pageId) {
		return memberPageLinkJpaRepository.existsByMemberAndPage(memberId, pageId);
	}

	@Override
	public long countMembersInSharedPage(Long pageId) {
		return memberPageLinkJpaRepository.countMembersInSharedPage(pageId);
	}

	@Override
	public long countHostMembersInSharedPage(Long pageId, Member member) {
		return memberPageLinkJpaRepository.countHostMembersInSharedPage(pageId, member);
	}

	@Override
	public void deleteByMemberId(Long memberId) {
		memberPageLinkJpaRepository.deleteByMemberId(memberId);
	}

	@Override
	public List<MemberPageLink> findUniqueHostByMemberId(Long memberId) {
		return memberPageLinkJpaRepository.findUniqueHostByMemberId(memberId);
	}

	@Override
	public MemberPageLink save(MemberPageLink memberPageLink) {
		return memberPageLinkJpaRepository.save(memberPageLink);
	}
}
