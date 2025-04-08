package com.linkmoa.source.domain.favorite.repository;

import java.util.List;

import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.linkmoa.source.domain.favorite.entity.Favorite;
import com.linkmoa.source.domain.member.entity.Member;

public interface FavoriteDataAccess {
	Favorite findByItemIdAndItemType(Long itemId, ItemType itemType);

	List<Favorite> findByMember(Member member);

	public void incrementOrderIndexesForMember(Member member);

	public void decrementFavoriteOrderIndexes(Integer orderIndex);

	void save(Favorite favorite);

	void delete(Favorite favorite);

}
