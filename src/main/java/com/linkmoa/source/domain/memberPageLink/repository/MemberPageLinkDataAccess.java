package com.linkmoa.source.domain.memberPageLink.repository;

import java.util.List;
import java.util.Optional;

import com.linkmoa.source.domain.member.entity.Member;
import com.linkmoa.source.domain.memberPageLink.constant.PermissionType;
import com.linkmoa.source.domain.memberPageLink.entity.MemberPageLink;
import com.linkmoa.source.domain.page.entity.Page;

public interface MemberPageLinkDataAccess {
	Optional<Page> findPersonalPageByMemberId(Long memberId);

	PermissionType findPermissionTypeByMemberIdAndPageId(Long memberId, Long pageId);

	void deleteByMemberIdAndPageId(Long memberId, Long pageId);

	Optional<MemberPageLink> findByMemberAndPage(Long memberId, Long pageId);

	boolean existsByMemberAndPage(Long memberId, Long pageId);

	long countMembersInSharedPage(Long pageId);

	long countHostMembersInSharedPage(Long pageId, Member member);

	void deleteByMemberId(Long memberId);

	List<MemberPageLink> findUniqueHostByMemberId(Long memberId);

	MemberPageLink save(MemberPageLink memberPageLink);

}
