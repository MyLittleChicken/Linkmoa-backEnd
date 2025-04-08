package com.linkmoa.source.domain.favorite.repository.rdb;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.linkmoa.source.domain.favorite.entity.Favorite;
import com.linkmoa.source.domain.member.entity.Member;

public interface FavoriteJpaRepository extends JpaRepository<Favorite, Long> {
	Favorite findByItemIdAndItemType(Long itemId, ItemType itemType);

	List<Favorite> findByMember(Member member);

}
